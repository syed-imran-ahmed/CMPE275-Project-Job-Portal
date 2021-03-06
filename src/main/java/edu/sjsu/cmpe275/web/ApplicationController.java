package edu.sjsu.cmpe275.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import edu.sjsu.cmpe275.email.ActivationEmail;
import edu.sjsu.cmpe275.model.Application;
import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.model.Interested;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.ApplicationService;
import edu.sjsu.cmpe275.service.CompanyJobsService;
import edu.sjsu.cmpe275.service.InterestedService;
import edu.sjsu.cmpe275.service.JobseekerService;
import edu.sjsu.cmpe275.service.UserService;

@Controller
public class ApplicationController {
    
    @Autowired
    private CompanyJobsService companyJobsService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JobseekerService jobseekerService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private InterestedService interestedService;
    
    private static String RESUME_FOLDER = "src/main/webapp/resumes/";

    /**
     * @param jobid
     * @param model
     * @param session
     * @return applying using the job id and will return the applyjob page
     */
    @RequestMapping(value = "/applyjob/{jobid}", method = RequestMethod.GET)
    public String applyJob(@PathVariable("jobid") Long jobid, Model model,HttpSession session) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
		CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        model.addAttribute("companyjobposts", jobPost);
        Application a = applicationService.findByJobIDAndJobseekerID(jobid,user.getId());
    	
        if(a != null){

	        if(a.getStatus().contains("Pending") || a.getStatus().contains("Offered"))
	        {
	        	model.addAttribute("reapply", false);
	        	System.out.println(a.getStatus());
	        	if (a.getStatus().contains("Offered"))
	        	{
	        		System.out.println(a.getId());
	        		model.addAttribute("offered", true);
	        		model.addAttribute("appid", a.getId());
	        	}
	        }
	        else
	        {
	        	model.addAttribute("reapply", true);
	        }

        }
        else{
        	model.addAttribute("offered", false);
        }
        System.out.println("no of pending:");
        System.out.println(applicationService.findByStatusAndJobseekerID("Pending", user.getId()).size());
        model.addAttribute("pendingCount",applicationService.findByStatusAndJobseekerID("Pending", user.getId()).size() );
        session.setAttribute("jobid", jobid);
        return "applyjob";
    }


    /**
     * @param model
     * @return get the apply job page for profile
     */
    @RequestMapping(value = "/applyprofile", method = RequestMethod.GET)
    public String profileget(Model model) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        model.addAttribute("jobseeker", js);
        return "applyprofile";
    }
    
    
    /**
     * @param model
     * @param session
     * @return apply using the profile
     */
    @PostMapping("/applyprofile")
    public String profilepost(Model model, HttpSession session) {
    	String resume = null;
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
    	String id = user.getId()+"a"+session.getAttribute("jobid");
        String name = js.getFirstname()+ " " +js.getLastname();
        long jobid=  (long) session.getAttribute("jobid");
        Application application = new Application(id,user.getId(),jobid,name,user.getEmailid(),resume,"Pending" );
        applicationService.save(application);
        
        CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        ActivationEmail.emailAppliedJob(user.getEmailid(), jobid,jobPost.getCompany().getName(),jobPost.getTitle(),jobPost.getDescrip(),jobPost.getLoc());
     
        return "redirect:welcome";
    }
    
    
    /**
     * @param file
     * @param session
     * @return apply using the resume
     * @throws IOException
     */
    @PostMapping("/applyresume")
    public String resumeUpload(@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
    	System.out.println("apply Resume");
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
            return "applyresume";
        }
        String id=null;
        Path path=null;
        String fileLoc =null;
        String filename =null;
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        try {

            byte[] bytes = file.getBytes();
            id = user.getId()+"a"+session.getAttribute("jobid");
            String fileName = file.getOriginalFilename();
            int lastIndex = fileName.lastIndexOf('.');
            String substring = fileName.substring(lastIndex, fileName.length());
            System.out.println("Success 1"+ substring);
            System.out.println("format:" + substring);

            List<String> validExtensions =  Arrays.asList(".docx",".doc",".pdf",".txt");
            if(!validExtensions.contains(substring)){
            	return "applyresume";
            }
            filename = id+substring;
            fileLoc= "resumes/" + filename;
            s3client.putObject(new PutObjectRequest(bucketName, fileLoc,is, new ObjectMetadata())
    				.withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.println("Success 1");
           // path = Paths.get(fileLoc);
            //Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Success 2");
        String name = js.getFirstname()+ " "+ js.getLastname();
        long jobid=  (long) session.getAttribute("jobid");
        Application application = new Application(id,user.getId(),jobid,name,user.getEmailid(),filename,"Pending" );
        applicationService.save(application);
        System.out.println(" Success 3");
        CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        ActivationEmail.emailAppliedJob(user.getEmailid(), jobid,jobPost.getCompany().getName(),jobPost.getTitle(),jobPost.getDescrip(),jobPost.getLoc());
        
        return "redirect:welcome";
    }
    
    
    /**
     * @return the apply resume page
     */
    @GetMapping("/applyresume")
    public String resumeget() {
  
        return "applyresume";
    }
    
    /**
     * @param model
     * @param session
     * @return cancel or reject the job
     */
    @RequestMapping(value = "/applicationView", method = RequestMethod.GET)
    public String cancelOrRejectApp(Model model,HttpSession session) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        List<Application> applicationList=applicationService.findByjobseekerID(user.getId());
        HashMap<Long,String> hm=new HashMap<>(); 
        for(Application a: applicationList)
        {
        	hm.put(a.getJobID(), companyJobsService.findByJobId(a.getJobID()).getCompany().getName()+" - "+companyJobsService.findByJobId(a.getJobID()).getTitle());
        }
        model.addAttribute("appList", applicationList);
        model.addAttribute("cmpHM", hm);
        
        return "applicationView";
    }
    
    /**
     * @param id
     * @return update the job application 
     */
    @Transactional
    @PostMapping("/applicationView")
    public String updateApp(@RequestParam List<String> id) {
    	if( id.isEmpty())
    	{
    		 return "applicationView";
    	}
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        HashMap<Long,String> hm = new HashMap<>();
    	for (String x : id){
    		 Application application = applicationService.findById(x);
    		 if(application.getStatus().equals("Pending"))
    		 {
    			 application.setStatus("Cancelled");
    			 hm.put(application.getJobID(), "Cancelled");
    		 }
    		 else if(application.getStatus().equals("Offered"))
    		 {
    			 application.setStatus("OfferRejected");
    			 hm.put(application.getJobID(), "OfferRejected");
    		 }
    		 applicationService.save(application);
    	}
    	
       ActivationEmail.emailModifiedApplicationStatus(user.getEmailid(),hm);
     
    	return "redirect:applicationView";
    }
    
    /**
     * @param offered
     * @param model
     * @return accept the offer from the company
     */
    @RequestMapping(value = "/acceptOffer/{offered}", method = RequestMethod.POST)
    public String acceptOffer(@PathVariable("offered") String offered,Model model) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        String[] splitted = offered.split("a");
        long jobseekerid = Long.parseLong(splitted[0]);
        long jobid = Long.parseLong(splitted[1]);
        CompanyJobPosts ajp = companyJobsService.findByJobId(jobid);
        //Verifying details before processing
        if ((ajp.getJobposition().contains("Open")) && (user.getId() == jobseekerid) && (applicationService.findById(offered).getStatus().contains("Offered")))
        {
        	Application app = applicationService.findById(offered);
        	app.setStatus("OfferAccepted");
        	applicationService.save(app);
        	User u= userService.findById(ajp.getCompany().getCid());
        	ActivationEmail.emailOfferAcceptance(u.getEmailid(),user.getEmailid(), jobid);        	
        }
        return "redirect:/applicationView";
    }
    
	/**
	 * @param model
	 * @param session
	 * @return list the interested jobs
	 */
	@RequestMapping(value = "/listInterested", method = RequestMethod.GET)
    public String listInterestedJobs(Model model,HttpSession session) {
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        
        List<Interested> interestedList=interestedService.findByJobseekerid(user.getId());
        List<CompanyJobPosts>jobslist = new ArrayList<CompanyJobPosts>();
        for(Interested i: interestedList) 
        {
        	CompanyJobPosts a=companyJobsService.findByJobId(i.getJobid());
        	jobslist.add(a);
        }
        HashMap<String,Boolean> hm =new HashMap<>(); 
		for(Interested i: interestedList ){
			if (i != null){
				String[] sub = i.getId().split("\\+");
				System.out.println(sub[1]);
				hm.put(sub[1], i.isStatus());
			}
		}
        model.addAttribute("jobslist", jobslist);
        model.addAttribute("interested", hm);
        return "listInterested";
    }
}
