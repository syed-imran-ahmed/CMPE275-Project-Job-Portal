package edu.sjsu.cmpe275.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.CompanyService;
import edu.sjsu.cmpe275.service.UserService;

@Controller
public class CompanyController {

	  @Autowired
	  private CompanyService companyService;
	  
	  @Autowired
	  private UserService userService;
	
	 @RequestMapping(value = "/company", method = RequestMethod.GET)
	    public String company(Model model) {
	        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company=companyService.findByCid(user.getId());
	        if(company != null){
	        	model.addAttribute("company", company);
	         }else{
	        	model.addAttribute("company", new Company());
	        }
	        return "company";
	    }
	    
	    @RequestMapping(value = "/company", method = RequestMethod.POST)
	    public String jobseeker(@ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
//	    	jobseekerValidator.validate(company, bindingResult);
	//
//	        if (bindingResult.hasErrors()) {
//	            return "company";
//	        }
	        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        company.setCid(user.getId());
	        companyService.save(company);

	        return "company";
	    }
}
