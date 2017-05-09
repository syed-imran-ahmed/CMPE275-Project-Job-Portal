package edu.sjsu.cmpe275.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sjsu.cmpe275.service.HibernateSearchService;

@Controller
public class SearchController {

	@Autowired
	HibernateSearchService service;
	
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	  public String searchWithString(String q, Model model) {
		if(q==null)
			return "search";
	    List<?> searchResults = null;
	    try {
	      searchResults = service.fuzzySearch(q);
	    }
	    catch (Exception ex) {
	      
	    }
	    model.addAttribute("jobslist", searchResults);
	    return "search";
	  }
}
