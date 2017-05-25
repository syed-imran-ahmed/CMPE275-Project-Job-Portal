package edu.sjsu.cmpe275.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import edu.sjsu.cmpe275.email.ActivationEmail;
import edu.sjsu.cmpe275.email.TokenGenerator;
import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.model.Image;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.Profile;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.CompanyJobsService;
import edu.sjsu.cmpe275.service.CompanyService;
import edu.sjsu.cmpe275.service.ImageService;
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
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private CompanyJobsService companyJobsService;
    
    @Autowired
    private ImageService imageService;
    
    private static String IMAGE_FOLDER = "gs://test-e1811.appspot.com/images/";
    
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
    	
    	System.out.println("INSIDE LOGIN");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(@RequestParam(required = false) Integer page,Model model) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
    	
    	List<String> jobPositions = new ArrayList<String>();
    	jobPositions.add("Open");
    	jobPositions.add("Filled");
    	jobPositions.add("Cancelled");
    	
    	if(user.getIsVerified()==null || user.getIsVerified().equals("NO"))
    	{
    		return "emailverification";
    	}
    	else 
    	{
    		if(user.getUsertype().equals("JobSeeker")){  

    			List<CompanyJobPosts> jobPosts = companyJobsService.findAllJobs();
    			JobSeeker js= null;
    			js = jobseekerService.findById(user.getId());
    			if (js != null)
    			{
    				System.out.println(js);
    				model.addAttribute("profileComplete", true);
    			}

    			PagedListHolder<CompanyJobPosts> pagedListHolder = new PagedListHolder<>(jobPosts);
    			pagedListHolder.setPageSize(4);
    			model.addAttribute("maxPages", pagedListHolder.getPageCount());

    			if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

    			model.addAttribute("page", page);
    			if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
    				pagedListHolder.setPage(0);
    				model.addAttribute("jobslist", pagedListHolder.getPageList());
    			}
    			else if(page <= pagedListHolder.getPageCount()) {
    				pagedListHolder.setPage(page-1);
    				model.addAttribute("jobslist", pagedListHolder.getPageList());
    			}

    			return "welcome";
    		}
    		else{

    			Company company = companyService.findByCid(user.getId());
    			if(company!=null)
    			{
    				List<String> jobTitles = new ArrayList<String>();

    				for(CompanyJobPosts jobPost:company.getJobPosts())
    				{
    					jobTitles.add(jobPost.getTitle());
    				}
    				model.addAttribute("companylogo",company.getLogo());

    				PagedListHolder<CompanyJobPosts> pagedListHolder = new PagedListHolder<>(company.getJobPosts());
    				pagedListHolder.setPageSize(4);
    				model.addAttribute("maxPages", pagedListHolder.getPageCount());

    				if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

    				model.addAttribute("page", page);
    				if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
    					pagedListHolder.setPage(0);
    					model.addAttribute("jobslist", pagedListHolder.getPageList());
    					model.addAttribute("jobposition", jobPositions);
    				}
    				else if(page <= pagedListHolder.getPageCount()) {
    					pagedListHolder.setPage(page-1);
    					model.addAttribute("jobslist", pagedListHolder.getPageList());
    					model.addAttribute("jobposition", jobPositions);
    				}

    			}

    			return "companywelcome";
    		}
    	}

    }
    
    
    @RequestMapping(value = "/selection", method = RequestMethod.POST)
	public String selection(@RequestParam(name="selectbox") String status,@RequestParam(required = false) Integer page, Model model) {
    	
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
    	Company company = companyService.findByCid(user.getId());
    	
    	List<String> jobPositions = new ArrayList<String>();
    	jobPositions.add("Open");
    	jobPositions.add("Filled");
    	jobPositions.add("Cancelled");
    	
		if(company!=null)
		{
			List<String> jobTitles = new ArrayList<String>();
			List<CompanyJobPosts> jobPosting = new ArrayList<CompanyJobPosts>();
			
			for(CompanyJobPosts jobPost:company.getJobPosts())
			{
				if(jobPost.getJobposition().equals(status)){
					jobPosting.add(jobPost);
				jobTitles.add(jobPost.getTitle());
				}
			}
			model.addAttribute("companylogo",company.getLogo());

			PagedListHolder<CompanyJobPosts> pagedListHolder = new PagedListHolder<>(jobPosting);
			pagedListHolder.setPageSize(4);
			model.addAttribute("maxPages", pagedListHolder.getPageCount());

			if(page==null || page < 1 || page > pagedListHolder.getPageCount())page=1;

			model.addAttribute("page", page);
			if(page == null || page < 1 || page > pagedListHolder.getPageCount()){
				pagedListHolder.setPage(0);
				model.addAttribute("jobslist", pagedListHolder.getPageList());
				model.addAttribute("jobposition", jobPositions);
			}
			else if(page <= pagedListHolder.getPageCount()) {
				pagedListHolder.setPage(page-1);
				model.addAttribute("jobslist", pagedListHolder.getPageList());
				model.addAttribute("jobposition", jobPositions);
			}

		}

		return "companywelcome";
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
    public String jobseekerget(Model model,HttpSession session,HttpServletResponse response ) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        if(js != null){
        	model.addAttribute("jobseeker", js);
         }else{
        	model.addAttribute("jobseeker", new JobSeeker());
        }
       // File f = new File(IMAGE_FOLDER + user.getId()+".JPG");
        System.out.println("Inside Upload "+ user.getId());
        model.addAttribute("image", imageService.findByJsid(user.getId()));
        if(session.getAttribute("id")==null) { 
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
    public String imageUpload(@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
    	AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJTRP2TGOOYXWLTHQ", 
				"FZnUn2F9/PuW6oRNHnvw36yNYLhlibmsDYxbLDz1");
		
		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
		// create bucket - name must be unique for all S3 users
		String bucketName = "cmpe275";
		InputStream is=file.getInputStream();
	
		
        if (file.isEmpty()) {
            System.out.println("its empty");
            return "redirect:job_seeker";
        }

        try {

            byte[] bytes = file.getBytes();
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(currentUserName);
            String uniqueID = UUID.randomUUID().toString();

            String fileName = "images/"+uniqueID+".JPG";
            System.out.println(fileName);
    		s3client.putObject(new PutObjectRequest(bucketName, fileName,is, new ObjectMetadata())
    				.withCannedAcl(CannedAccessControlList.PublicRead));
            Image img = new Image(user.getId(),uniqueID);
            imageService.save(img);
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
    
    
    @ExceptionHandler(Exception.class)
	public String  handleException(Exception ex) {

		ModelAndView model = new ModelAndView("error");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String Url= request.getHeader("Referer");
		model.addObject("link",Url);
		return "error";

	}
 
}
