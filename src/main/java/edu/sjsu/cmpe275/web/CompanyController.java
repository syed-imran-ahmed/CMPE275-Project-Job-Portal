package edu.sjsu.cmpe275.web;

import java.util.ArrayList;
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

/**
* <h1>Company Controller Endpoints</h1>
* The company controller class provides the REST endpoints
* to map the basic GET,POST,PUT and DELETE request for
* its corresponding operations. It serves as the endpoints
* for all the operations for company role 
*
* @author  Syed Imran Ahmed
* @version 1.0
* @since   2017-05-03
*/ 
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

	
	 /**
	 * @param model
	 * @return company page if existing company or make a new company model
	 */
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
	    
	
	    /**
	     * @param company
	     * @param bindingResult
	     * @param model
	     * @return redirect to the home page upon saving the company attributes
	     */
	    @RequestMapping(value = "/company", method = RequestMethod.POST)
	    public String companyProfile(@ModelAttribute("company") Company company, BindingResult bindingResult, Model model) {

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
	    
	    
	    /**
	     * @param model
	     * @return show the welcome page of the company
	     */
	    @RequestMapping(value = "/companywelcome", method = RequestMethod.GET)
	    public String welcomecmpny(Model model) {
	    	return "welcome";
	    }
	    
	    /**
	     * @param model
	     * @return Show the post job page for the company
	     */
	    @RequestMapping(value = "/postjob", method = RequestMethod.GET)
	    public String companyJob(Model model) {
	       
	        model.addAttribute("companyjobposts", new CompanyJobPosts());
	        return "postjob";
	    }
	    
	    
	    /**
	     * @param cmpJobPost
	     * @param bindingResult
	     * @param model
	     * @return Check if the model already has the job details, if so then show those for editing else post a new 
	     * job. Also check if the position is filled or cancelled then trigger a mail to the applications and then save the 
	     * job and return.
	     */
	    @RequestMapping(value = "/postjob", method = RequestMethod.POST)
	    public String companyPostJob(@ModelAttribute("companyjobposts") CompanyJobPosts cmpJobPost, BindingResult bindingResult, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);
	        Company company = companyService.findByCid(user.getId());
	        cmpJobPost.setCompany(company);
	       if(cmpJobPost.getJobid()==null)
	       {
	    	   companyJobsService.save(cmpJobPost);
	    	   return "redirect:/";
	       }
	        List<Application> app =  appService.findByjobID(cmpJobPost.getJobid());
            List<String> test= new ArrayList<String>();
            test.add("false");

	        if( app != null){
	        	for ( Application a: app)
	        	{
                    if(a.getStatus().equals("OfferAccepted") && cmpJobPost.getJobposition().equals("Cancelled"))
                    {
                        model.addAttribute("cannotcancel", test);
                        model.addAttribute("jobid", cmpJobPost.getJobid());
                        model.addAttribute("jobposition", cmpJobPost.getJobposition());
                        model.addAttribute("companyjobposts", cmpJobPost);
                        return "postjob";
                    }

	        		
	        		if(cmpJobPost.getJobposition().contains("Filled") || cmpJobPost.getJobposition().contains("Cancelled"))
	        		{
	        			if(a.getStatus().equals("Pending") || a.getStatus().equals("Offered"))
		        		{
		        			a.setStatus("Cancelled");
		        		}
	        		}
	        		
	        		ActivationEmail.emailModifiedJobTrigger(a.getJobseekerEmail(),a.getJobID());
	        	}
	        }
	        if(cmpJobPost.getJobposition().contains("Filled") || cmpJobPost.getJobposition().contains("Cancelled")){
	        	List<Interested> intr=intrstd.findByJobid(cmpJobPost.getJobid());
	        	for(Interested i: intr){
	        		intrstd.removeByJobid(i.getJobid());
	        	}
	        }
	        
	        companyJobsService.save(cmpJobPost);
	        return "redirect:/";
	    }
	    
	    /**
	     * @param jobid
	     * @param model
	     * @return Show the post job page for that particular job id
	     */
	    @RequestMapping(value = "/postjob/{jobid}", method = RequestMethod.GET)
	    public String companyShowJob(@PathVariable("jobid") Long jobid, Model model) {
	       
	    	CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
	    	model.addAttribute("jobid", jobPost.getJobid());
	    	model.addAttribute("jobposition", jobPost.getJobposition());
	        model.addAttribute("companyjobposts", jobPost);
	        return "postjob";
	    }
	    
	    /**
	     * @param jobid
	     * @param model
	     * @return Show all the applications related to that job id for which the jobseekr has applied for
	     */
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
	    
	    /**
	     * @param jobId
	     * @param jobSeekerId
	     * @param model
	     * @return Show the resume and profile view to the company and the company can download the applicants resume and 
	     * show the schedule interview link along with job offered or rejected button.
	     */
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
	    
	    /**
	     * @param jsApplication
	     * @param bindingResult
	     * @param model
	     * @return Save the jobseeker attributes if or not he has been offered the job or rejected.
	     */
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
	    
	    /**
	     * @param jobAppId
	     * @param model
	     * @return show the view for scheduling the interview in case the company wants to interview the applicant
	     * then redirect it to the home page of the company.
	     */
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
	    
	    /**
	     * @param interview
	     * @param bindingResult
	     * @param model
	     * @return schedule the interview for the user and send out the notifications
	     */
	    @RequestMapping(value = "/interviewSchedule", method = RequestMethod.POST)
	    public String saveInterview(@ModelAttribute("interview") Interview interview, BindingResult bindingResult, Model model) {
	    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userService.findByUsername(currentUserName);	
	        intrw.save(interview);
	        System.out.println(interview.getInterviewDate());
	    	String[] subtr =interview.getId().split("a");
	    	long jobId = Long.parseLong(subtr[1]);
	    	long jobseekerId = Long.parseLong(subtr[0]);
	    	//ActivationEmail.emailInterview(user.getEmailid(),userService.findById(jobseekerId).getEmailid(),jobId, interview);
	    	String url= "redirect:jobApplications/"+jobId;
	    	return url;
	    }
}
