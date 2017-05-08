package edu.sjsu.cmpe275.service;
import java.util.List;

import edu.sjsu.cmpe275.model.CompanyJobPosts;;

public interface CompanyJobsService {
	   
	void save(CompanyJobPosts companyJobPosts);
	    
	CompanyJobPosts findByJobId(long id);

	List<CompanyJobPosts> findAllJobs();
}
