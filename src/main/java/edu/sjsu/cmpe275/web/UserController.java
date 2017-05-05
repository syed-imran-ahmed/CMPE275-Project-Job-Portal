package edu.sjsu.cmpe275.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.sjsu.cmpe275.email.ActivationEmail;
import edu.sjsu.cmpe275.email.TokenGenerator;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.Profile;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.JobseekerService;
import edu.sjsu.cmpe275.service.SecurityService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.validator.JobseekerValidator;
import edu.sjsu.cmpe275.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JobseekerService jobseekerService;
    
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private JobseekerValidator jobseekerValidator;  

    private static String IMAGE_FOLDER = "src/main/webapp/images/";
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        String tokenID = TokenGenerator.randomToken();
        userForm.setTokenId(tokenID.substring(0, 6));
 
        ActivationEmail.emailRecommendTrigger(userForm.getEmailid(), userForm.getTokenId());
        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        if(user.getIsVerified()==null || user.getIsVerified().equals("NO"))
        {
        	return "emailverification";
        }
        else 
        {
        	if(user.getUsertype().equals("JobSeeker")){   	
        	return "welcome";
        	}
        else{
        	return "companywelcome";
        }
    }
    }
    
    @RequestMapping(value = "/verification", method = RequestMethod.POST)
	public String verification( @RequestParam("token") String token, Model model) {
    	
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        if(token!=null && user.getTokenId().equals(token))
        {
        	String verified = "You have successfully verified your mail-Id.";
           	userService.saveUserVerification("YES");
           	ActivationEmail.emailAckTrigger(user.getEmailid(), verified);
           	
           	if(user.getUsertype().equals("JobSeeker")){   	
            	return "welcome";
            	}
            else{
            	return "companywelcome";
            }
        }
        else
        {
        	model.addAttribute("error", "The verification code entered is invalid. Please enter valid code.");
        	return "emailverification";
        }
        	
    	
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        if(user.getProfile() != null){
        	model.addAttribute("firstName", user.getProfile().getFirstName());
        	model.addAttribute("lastName", user.getProfile().getLastName());
        	model.addAttribute("profileForm", user.getProfile());
        }else{
        	model.addAttribute("profileForm", new Profile());
        }
        return "profile";
    }
    
//    
//    @RequestMapping(value = "/profile", method = RequestMethod.POST)
//    public String profile(@ModelAttribute("profileForm") Profile profileForm, BindingResult bindingResult, Model model) {
//        profileValidator.validate(profileForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "profile";
//        }
//        
//        userService.saveProfile(profileForm);
//
//        return "profile";
//    }
    
    
    @RequestMapping(value = "/job_seeker", method = RequestMethod.GET)
    public String jobseeker(Model model,HttpSession session) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        if(js != null){
        	model.addAttribute("jobseeker", js);
         }else{
        	model.addAttribute("jobseeker", new JobSeeker());
        }
        File f = new File(IMAGE_FOLDER + user.getId()+".JPG");
        if(f.exists() && !f.isDirectory() && session.getAttribute("id")==null) { 
        	session.setAttribute("id", user.getId());
        }
        return "job_seeker";
    }
    
    @RequestMapping(value = "/job_seeker", method = RequestMethod.POST)
    public String jobseeker(@ModelAttribute("jobseeker") JobSeeker j,BindingResult bindingResult, Model model,Object command) {
    	jobseekerValidator.validate(j, bindingResult);

        if (bindingResult.hasErrors()) {
            return "job_seeker";
        }
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        j.setId(user.getId());
        
        jobseekerService.save(j);

        return "job_seeker";
    }
    
    @PostMapping("/upload")
    public String imageUpload(@RequestParam("file") MultipartFile file,HttpSession session) {

        if (file.isEmpty()) {
            System.out.println("its empty");
            return "job_seeker";
        }

        try {

            byte[] bytes = file.getBytes();
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(currentUserName);
            Path path = Paths.get(IMAGE_FOLDER + user.getId()+".JPG");
            Files.write(path, bytes);
            session.setAttribute("id", user.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:job_seeker";
    }
    
    @GetMapping("/upload")
    public String uploadStatus() {
  
        return "upload";
    }
   
}
