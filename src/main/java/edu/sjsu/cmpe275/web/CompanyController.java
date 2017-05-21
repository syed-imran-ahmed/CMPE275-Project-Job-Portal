package edu.sjsu.cmpe275.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sjsu.cmpe275.email.ActivationEmail;
import edu.sjsu.cmpe275.model.Application;
import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.model.Interested;
import edu.sjsu.cmpe275.model.Interview;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.ApplicationService;
import edu.sjsu.cmpe275.service.CompanyJobsService;
import edu.sjsu.cmpe275.service.CompanyService;
import edu.sjsu.cmpe275.service.InterestedService;
import edu.sjsu.cmpe275.service.InterviewService;
import edu.sjsu.cmpe275.service.JobseekerService;
import edu.sjsu.cmpe275.service.UserService;
import edu.sjsu.cmpe275.validator.CompanyValidator;

@Controller
public class CompanyController {

	  @Autowired
	  private CompanyService companyService;
	  
	  @Autowired
	  private JobseekerService jsService;
	  
	  @Autowired
	  private UserService userService;
	  
	  @Autowired
	  private CompanyJobsService companyJobsService;
	  
	  @Autowired
	  private ApplicationService appService;

	  @Autowired
	  private CompanyValidator companyValidator;
	  
	  @Autowired
	  private InterestedService intrstd;
	  
	  @Autowired
	  private InterviewService intrw;

	
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
	    public String companyProfile(@ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {
//	    	jobseekerValidator.validate(company, bindingResult);
	//
//	        if (bindingResult.hasErrors()) {
//	            return "company";
//	        }
	    	  companyValidator.validate(company, bindingResult);

	          if (bindingResult.hasErrors()) {
	              return "company";
	          }
	        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        company.setCid(user.getId());
	        companyService.save(company);

	        return "redirect:/";
	    }
	    
	    
	    @RequestMapping(value = "/companywelcome", method = RequestMethod.GET)
	    public String welcome(Model model) {
	    	return "welcome";
	    }
	    
	    @RequestMapping(value = "/postjob", method = RequestMethod.GET)
	    public String companyJob(Model model) {
	       
	        model.addAttribute("companyjobposts", new CompanyJobPosts());
	        return "postjob";
	    }
	    
	    
	    @RequestMapping(value = "/postjob", method = RequestMethod.POST)
	    public String companyPostJob(@ModelAttribute("companyjobposts") CompanyJobPosts cmpJobPost, BindingResult bindingResult, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company = companyService.findByCid(user.getId());
	        cmpJobPost.setCompany(company);
	        companyJobsService.save(cmpJobPost);
	        List<Application> app =  appService.findByjobID(cmpJobPost.getJobid());
	        if( app != null){
	        	for ( Application a: app)
	        	{
	        		ActivationEmail.emailModifiedJobTrigger(a.getJobseekerEmail(),a.getJobID());
	        	}
	        }
	        if(cmpJobPost.getJobposition().contains("Filled") || cmpJobPost.getJobposition().contains("Cancelled")){
	        	List<Interested> intr=intrstd.findByJobid(cmpJobPost.getJobid());
	        	for(Interested i: intr){
	        		intrstd.removeByJobid(i.getJobid());
	        	}
	        }
	        return "redirect:/";
	    }
	    
	    @RequestMapping(value = "/postjob/{jobid}", method = RequestMethod.GET)
	    public String companyJob(@PathVariable("jobid") Long jobid, Model model) {
	       
	    	CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
	    	model.addAttribute("jobid", jobPost.getJobid());
	    	model.addAttribute("jobposition", jobPost.getJobposition());
	        model.addAttribute("companyjobposts", jobPost);
	        return "postjob";
	    }
	    
	    @RequestMapping(value = "/jobApplications/{jobid}", method = RequestMethod.GET)
	    public String viewApplicantList(@PathVariable("jobid") Long jobid, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company = companyService.findByCid(user.getId());
	        //To make sure one company doesn't modify jobId in URL to check other company's jobs
	        if(company.getCid()== companyJobsService.findByJobId(jobid).getCompany().getCid()){
	        	List<Application> applicantionList=appService.findByjobID(jobid);
	        	model.addAttribute("appList", applicantionList);
	        	model.addAttribute("jobid", jobid);
	        	return "jobApplications";
	        }
	        else {
	        	return "redirect:/";
	        }
	    }
	    
	    @RequestMapping(value = "/jobseekerOffer/{jobId}/{jobSeekerId}", method = RequestMethod.GET)
	    public String viewApplicant(@PathVariable("jobId") Long jobId,@PathVariable("jobSeekerId") Long jobSeekerId, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company = companyService.findByCid(user.getId());
	        //To make sure one company doesn't modify jobId in URL to check other company's jobs
	        if(company.getCid()== companyJobsService.findByJobId(jobId).getCompany().getCid()){
		    	JobSeeker js=jsService.findById(jobSeekerId);
		        Application jsApplication=appService.findByJobIDAndJobseekerID(jobId, jobSeekerId);
		        if (jsApplication.getJobseekerResumeLoc() == null){
		        	model.addAttribute("jobseeker", js);
		        	model.addAttribute("profile", true);
		        }
		        else{
		        	String resumeLoc  = jsApplication.getJobseekerResumeLoc();
		        	String[] subtr =resumeLoc.split("\\.");
		        	System.out.println(subtr);
		        	model.addAttribute("type", subtr[1]);
		        }
		        model.addAttribute("jsApplication", jsApplication);
			    return "jobseekerOffer";
		        
	        }
	        else{
	        	return "redirect:/";
	        }
	    }
	    
	    @RequestMapping(value = "/jobseekerOffer", method = RequestMethod.POST)
	    public String saveDecision(@ModelAttribute("jsApplication") Application jsApplication, BindingResult bindingResult, Model model) {
	    	if (jsApplication.getStatus()!= null && jsApplication.getStatus()!= "Pending"){
	    		Application app = appService.findById(jsApplication.getId());
	    		String status = null;
	    		if(jsApplication.getStatus().contains("Offer"))
	    			
	    		{	
	    			app.setStatus("Offered");
	    			status = "Offered";
	    		}
	    		else if(jsApplication.getStatus().contains("Reject"))
	    		{
	    			app.setStatus("Rejected");
	    			status = "Rejected";
	    		}
	    		appService.save(app);
	    		String url = "redirect:jobApplications/"+app.getJobID();
	    		ActivationEmail.emailOffer(app.getJobseekerEmail(),app.getJobID(), status);
	    		return url;
	    	}
	    	return "redirect:/";
	    }
	    
	    @RequestMapping(value = "/interviewSchedule/{jobAppId}", method = RequestMethod.GET)
	    public String scheduleInterview(@PathVariable("jobAppId") String jobAppId, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company = companyService.findByCid(user.getId());
	        Application appl= appService.findById(jobAppId);
	        long jobId= appl.getJobID();
	        //To make sure one company doesn't modify jobId in URL to check other company's jobs
	        if(company.getCid()== companyJobsService.findByJobId(jobId).getCompany().getCid()){
	        	Interview intv = intrw.findById(jobAppId);
	        	if(intv == null){
	        		intv = new Interview();
	        		intv.setId(jobAppId);
	        		intv.setCandidateName(appl.getJobseekerName());
	        		intv.setStatus("invitation sent");
	        	}
	        	model.addAttribute("interview", intv);
	        	return "interviewSchedule";
	        }
	        return "redirect:/";
	    }
	    
	    @RequestMapping(value = "/interviewSchedule", method = RequestMethod.POST)
	    public String saveInterview(@ModelAttribute("interview") Interview interview, BindingResult bindingResult, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);	
	        intrw.save(interview);
	        System.out.println(interview.getInterviewDate());
	    	String[] subtr =interview.getId().split("\\a");
	    	long jobId = Long.parseLong(subtr[1]);
	    	long jobseekerId = Long.parseLong(subtr[0]);
	    	//ActivationEmail.emailInterview(user.getEmailid(),userService.findById(jobseekerId).getEmailid(),jobId, interview);
	    	String url= "redirect:jobApplications/"+jobId;
	    	return url;
	    }
}
