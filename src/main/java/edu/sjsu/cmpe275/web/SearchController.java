package edu.sjsu.cmpe275.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.HibernateSearchService;
import edu.sjsu.cmpe275.service.InterestedService;
import edu.sjsu.cmpe275.service.UserService;

/**
* <h1>Search Controller Endpoints</h1>
* The search controller class provides the REST endpoints
* to map the basic GET,POST,PUT and DELETE request for
* its corresponding operations. It serves as the endpoints
* for all the operations for search which is based on 
* Lucene Hibernate searching 
*
* @author  Syed Imran Ahmed
* @version 1.0
* @since   2017-05-05
*/ 

@Controller
public class SearchController {

	@Autowired
	HibernateSearchService service;
	
	@Autowired
	InterestedService intr;
    
	@Autowired
    private UserService userService;
	static List<CompanyJobPosts> returnList = new ArrayList<CompanyJobPosts>();
	/**
	 * @param q
	 * @param locations
	 * @param title
	 * @param salary
	 * @param company
	 * @param request
	 * @param model
	 * @return Show the search results for the search performed using the search box and in combination of 
	 * the filters.
	 */
	@RequestMapping(value="/search",method=RequestMethod.GET)
	  public String filterJob(@RequestParam(required = false) Integer page,
			  String q,
			  @RequestParam(name="checkboxName",defaultValue="")String[] locations,
			  @RequestParam(name="checkboxTitle", defaultValue="")String[] title,
			  @RequestParam(name="checkboxSal", defaultValue="")String[] salary,
			  @RequestParam(name="checkboxComp", defaultValue="")String[] company,
			  HttpServletRequest request,
			  Model model) {
		
		List<?> x= service.locationFacet(new String[0]);
		List<?> y= service.titleFacet(new String[0]);
		List<?> z= service.salaryFacet(new String[0]);
		List<?> f= service.companyFacet(new String[0]);
		
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
		
		if(q==null&&locations.length==0&&title.length==0&&salary.length==0&&company.length==0&page==null){
			    model.addAttribute("filter", x);
			    model.addAttribute("title", y);
			    model.addAttribute("salar", z);
			    model.addAttribute("company", f);
			    returnList.clear();
			return "search";
		}
		else if(page!=null && returnList.size()!=0)
		{
			model.addAttribute("filter", x);
			model.addAttribute("title", y);
			model.addAttribute("salar", z);
			model.addAttribute("company", f);
			model.addAttribute("interested", hm);
			
			
			PagedListHolder<CompanyJobPosts> pagedListHolder = new PagedListHolder<>(returnList);
			pagedListHolder.setPageSize(10);
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
			return "search";
		}
		else if(page==null)
		{
			returnList.clear();
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
			
		if(resList.size()!=0 && (searchResults!=null && searchResults.size()!=0))
		{
			resList.retainAll(searchResults);
		}
		else if(resList.size()==0 && searchResults.size()!=0 && (locations.length==0 || title.length==0 || salary.length==0 || company.length==0))
		{
			resList.addAll(searchResults);
		}
			
		resSet.addAll(resList);
		
		
		
		for(CompanyJobPosts jobPost: resSet)
		{
			returnList.add(jobPost);
		}
		
		
		//model.addAttribute("jobslist", returnList);
		model.addAttribute("filter", x);
		model.addAttribute("title", y);
		model.addAttribute("salar", z);
		model.addAttribute("company", f);
		model.addAttribute("interested", hm);
		
		
		PagedListHolder<CompanyJobPosts> pagedListHolder = new PagedListHolder<>(returnList);
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
		

		return "search";
	}
	
	/**
	 * @param jobid
	 * @param redirectAttributes
	 * @param request
	 * @param model
	 * @return If the job the user finds interesting he/she can mark it interested. 
	 */
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

	
	/**
	 * @param jobid
	 * @param model
	 * @return Removes the uninterested jobs from the list
	 */
	@RequestMapping(value="interestedremove/{jobid}",method=RequestMethod.POST)
	public String updateStatus(@PathVariable("jobid") long jobid,Model model)
	{	

		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userService.findByUsername(currentUserName);
    	String id = user.getId()+"+"+jobid;
		intr.removeById(id);
		return "redirect:/listInterested";

	}	
}
