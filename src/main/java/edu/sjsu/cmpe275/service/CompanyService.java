package edu.sjsu.cmpe275.service;
import edu.sjsu.cmpe275.model.Company;

public interface CompanyService {
	   
	void save(Company company);
	    
	Company findByCid(long id);

}
