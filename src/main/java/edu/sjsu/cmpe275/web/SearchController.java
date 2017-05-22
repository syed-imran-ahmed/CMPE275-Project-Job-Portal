package edu.sjsu.cmpe275.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.search.query.facet.Facet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.model.Interested;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.CompanyJobsServiceImpl;
import edu.sjsu.cmpe275.service.HibernateSearchService;
import edu.sjsu.cmpe275.service.InterestedService;
import edu.sjsu.cmpe275.service.SearchService;
import edu.sjsu.cmpe275.service.UserService;

@Controller
public class SearchController {

	@Autowired
	HibernateSearchService service;
	
	@Autowired
	InterestedService intr;
    
	@Autowired
    private UserService userService;

	@RequestMapping(value="/search",method=RequestMethod.GET)
	  public String filterJob(String q,@RequestParam(name="checkboxName",defaultValue="")String[] locations,
			  @RequestParam(name="checkboxTitle", defaultValue="")String[] title,
			  @RequestParam(name="checkboxSal", defaultValue="")String[] salary,
			  @RequestParam(name="checkboxComp", defaultValue="")String[] company,
			  HttpServletRequest request,
			  Model model) {
		
		
		List<?> x= service.locationFacet(new String[0]);
		List<?> y= service.titleFacet(new String[0]);
		List<?> z= service.salaryFacet(new String[0]);
		List<?> f= service.companyFacet(new String[0]);
		if(q==null&&locations.length==0&&title.length==0&&salary.length==0&&company.length==0){
			    model.addAttribute("filter", x);
			    model.addAttribute("title", y);
			    model.addAttribute("salar", z);
			    model.addAttribute("company", f);
			return "search";
		}
		List<CompanyJobPosts> searchResults = null;
		 try {
		      searchResults = service.fuzzySearch(q);
		    }
		    catch (Exception ex) {
		      
		    }
		
		
		
		List<CompanyJobPosts> resList = new ArrayList<CompanyJobPosts>();
		Set<CompanyJobPosts> resSet = new HashSet<CompanyJobPosts>();
		Set<Long> resSetOfId = new HashSet<Long>();
		if(locations.length!=0)
		{
			List<List<CompanyJobPosts>> jobLocations = (List<List<CompanyJobPosts>>) service.locationFacet(locations);
			
			for(List<CompanyJobPosts> jobPosts: jobLocations)
			{
				resList.addAll(jobPosts);
			}
			
			
			//TODO: show only job posts which are open
		}
	
		if(title.length!=0)
		{
			List<List<CompanyJobPosts>> jobTitles = (List<List<CompanyJobPosts>>) service.titleFacet(title);
			List<CompanyJobPosts> resListTitle = new ArrayList<CompanyJobPosts>();
			for(List<CompanyJobPosts> jobPosts: jobTitles)
			{
				resListTitle.addAll(jobPosts);
			}
			if(resList.size()!=0)
			{
				resList.retainAll(resListTitle);
			}
			else
				resList.addAll(resListTitle);
			//TODO: show only job posts which are open
		}
		
		if(salary.length!=0)
		{
			List<CompanyJobPosts> jobSalary = (List<CompanyJobPosts>) service.salaryFacet(salary);
			List<CompanyJobPosts> resListSalary = new ArrayList<CompanyJobPosts>();
			resListSalary.addAll(jobSalary);
			if(resList.size()!=0)
			{
				resList.retainAll(resListSalary);
			}
			else
				resList.addAll(resListSalary);
			
			//TODO: show only job posts which are open
		}
		
		if(company.length!=0)
		{
			List<List<Company>> jobCompany = (List<List<Company>>) service.companyFacet(company);
			List<Company> resListCompany = new ArrayList<Company>();
			List<CompanyJobPosts> allJobPosts = new ArrayList<CompanyJobPosts>();
			for(List<Company> jobcmp: jobCompany)
			{
				resListCompany.addAll(jobcmp);
			}
			
			for(Company c:resListCompany)
			{
				allJobPosts.addAll(c.getJobPosts());
			}
			
			if(resList.size()!=0)
			{
				resList.retainAll(allJobPosts);
			}
			else
				resList.addAll(allJobPosts);
			
			//TODO: show only job posts which are open
		}
			
		if(resList.size()!=0 && searchResults.size()!=0)
		{
			resList.retainAll(searchResults);
		}
		else if(resList.size()==0 && searchResults.size()!=0 && (locations.length==0 || title.length==0 || salary.length==0 || company.length==0))
		{
			resList.addAll(searchResults);
		}
		
			
			
		resSet.addAll(resList);
		
		List<CompanyJobPosts> returnList = new ArrayList<CompanyJobPosts>();
		
		for(CompanyJobPosts jobPost: resSet)
		{
			returnList.add(jobPost);
		}
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
        HashMap<String,Boolean> hm =new HashMap<>(); 
		for(Interested i: intr.findByJobseekerid(user.getId()) ){
			if (i != null){
				String[] sub = i.getId().split("\\+");
				System.out.println(sub[1]);
				hm.put(sub[1], i.isStatus());
			}
		}
		
		model.addAttribute("jobslist", returnList);
		model.addAttribute("filter", x);
		model.addAttribute("title", y);
		model.addAttribute("salar", z);
		model.addAttribute("company", f);
		model.addAttribute("interested", hm);

		return "search";
	}
	
	@RequestMapping(value="interestedapply/{jobid}",method=RequestMethod.POST)
	public String updateStatus(@PathVariable("jobid") long jobid,
			RedirectAttributes redirectAttributes, HttpServletRequest request,Model model)
	{	

		String url=request.getHeader("Referer");
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
    	String id = user.getId()+"+"+jobid;
		Interested interstd = intr.findById(id);
		if(interstd == null){
			interstd = new Interested(true,id,user.getId(),jobid);
		}
		else if(interstd.isStatus()){
			intr.removeById(id);
		}

		intr.save(interstd);
		return "redirect:"+ url;
	}

	@RequestMapping(value="interestedremove/{jobid}",method=RequestMethod.POST)
	public String updateStatus(@PathVariable("jobid") long jobid,Model model)
	{	

		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
    	String id = user.getId()+"+"+jobid;
		intr.removeById(id);
		return "redirect:/listInterested";

	}


	/*@RequestMapping(value="/filter",method=RequestMethod.GET)
	  public String filterJob(@RequestParam(name="checkboxName", defaultValue="")String[] q, @RequestParam(name="checkboxTitle", defaultValue="")String[] r,@RequestParam(name="checkboxSal", defaultValue="")String[] s,@RequestParam(name="checkboxComp", defaultValue="")String[] comp,Model model) {
		List<?> x= service.locationFacet();
		List<?> y= service.titleFacet();
		List<Facet> z= service.salaryFacet();
		List<Facet> f= service.companyFacet();
		List<Long> Final =new ArrayList<Long>();
		SearchService ss=new SearchService();
		String temp[] = new String[2];
		int temp_flag = 0;
		String arr[] = new String[1];
		for(int i =0;i<s.length;i++) {
			//System.out.println(s[i]+" Size"+s.length);
			if(!s[i].contains(",") && s[i].startsWith("[")){
				int temp_0 = Integer.parseInt(s[i].toString().replace("[", ""));
				temp[0] = String.valueOf(temp_0);
				temp_flag++;
				System.out.println("temp 0 : "+ temp[0]);
			}
			if(!s[i].contains(",") && s[i].endsWith("]")){
				int temp_1 = Integer.parseInt(s[i].toString().replace("]", ""));
				temp[1] = String.valueOf(temp_1);
				temp_flag++;
	//			System.out.println("temp 1: "+ temp[1]);
			}
			
		}
//		for(int k = 0; k<arr.length; k++) System.out.println("(Arpit) k: "+arr[k]);
		boolean flag=false;	
//		System.out.println("Checking q" +q.toString());
//		System.out.println("Checking r" +r.length);
		long def=0;
		if((q.length!=0)){
			flag= true;
			Final=ss.findFrom(q);
				Final.add(def);			
	  	}	
		if(comp.length!=0 && flag==false){ // working for comany
			flag=true;
			List<Long> companyId=ss.findCompanyId(comp);
			Final= ss.findJobId(companyId);
			Final.add(def);
			//for(int k =0;k<jobid.size();k++) System.out.println("here we are"+jobid.get(k));
			//=
			//Final.retainAll(titleObj);
			}
		if(comp.length!=0 && flag==true){
			List<Long> companyId=ss.findCompanyId(comp);
			List<Long> CompanyObj=ss.findJobId(companyId);
			Final.retainAll(CompanyObj);
			Final.add(def);
			}
		// till here
		if(r.length!=0 && flag==true){
		List<Long> titleObj=ss.findTitle(r);
		Final.retainAll(titleObj);
		Final.add(def);
		}
		
		if(r.length!=0 && flag==false){
		flag=true;
		Final=ss.findTitle(r);
		Final.add(def);
		}
		if(arr.length==1 && flag==true &&temp_flag>0)
		{
		arr[0] = "["+temp[0]+", "+temp[1]+"]"; // [10, 20]
	//	System.out.println("Yahaan hai arr[0]:: "+arr[0]);
		List<Long> check1=ss.findSal(arr);
		Final.retainAll(check1);
		}
	if(s.length!=0 && flag==true && temp_flag==0){
	//	System.out.println("wahaaan pr hai");
		List<Long> s111=ss.findSal(s);
		Final.retainAll(s111);
	}
	if(arr.length!=0 && flag==false &&temp_flag>0 ){
		arr[0] = "["+temp[0]+", "+temp[1]+"]"; // [10, 20]
	//	System.out.println("Yahaan hai arr[0]:: "+arr[0]);
		flag=true;
		Final=ss.findSal(arr);
	}
	if(s.length!=0 && flag==false && temp_flag==0 ){
		flag=true;
		Final=ss.findSal(s);
	}
	else if(r.length==0 && q.length==0 &&s.length==0 && comp.length==0){
		flag=true;
		System.out.println("Both values are null");
		model.addAttribute("filter", x);
	    model.addAttribute("title", y);
	    model.addAttribute("salar", z);
	    model.addAttribute("company", f);
		return "filter";
	}	
	//for(int i =0;i<Final.size();i++) System.out.println(Final.get(i));
		List<CompanyJobPosts> resList=new ArrayList<CompanyJobPosts>();
		List<CompanyJobPosts> FilledList=new ArrayList<CompanyJobPosts>();
		if(Final.contains(def)) Final.remove(def);
		for(int i =0;i<Final.size();i++) System.out.println(Final.get(i));
		for(Long l: Final ){
		resList.addAll(ss.findOpenJob(l));
		}
		for(Long l: Final ){
			FilledList.addAll(ss.findFilledJob(l));
			}  
	    model.addAttribute("jobslist", resList);
	    model.addAttribute("Filledlist", FilledList);
	    model.addAttribute("filter", x);
	    model.addAttribute("title", y);
	    model.addAttribute("salar", z);
	    model.addAttribute("company", f);
	    return "filter";
	  } */
	
}
