package com.cmpe295.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cmpe295.model.Profile;
import com.cmpe295.model.User;
import com.cmpe295.service.SecurityService;
import com.cmpe295.service.UserService;
import com.cmpe295.validator.ProfileValidator;
import com.cmpe295.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private ProfileValidator profileValidator;


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
        return "welcome";
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
    
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profile(@ModelAttribute("profileForm") Profile profileForm, BindingResult bindingResult, Model model) {
        profileValidator.validate(profileForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "profile";
        }
        
        userService.saveProfile(profileForm);

        return "profile";
    }
}
