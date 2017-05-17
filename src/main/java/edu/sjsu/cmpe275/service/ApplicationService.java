package edu.sjsu.cmpe275.service;

import java.util.List;

import edu.sjsu.cmpe275.model.Application;

public interface ApplicationService {

	void save(Application application);
	
	Application findById(String Id);
	
	List<Application> findByjobID(long Id);
	
	List<Application> findByjobseekerID(long Id);
	
	List<Application> findByStatusAndJobseekerID(String status, long Id);
	
	Application findByJobIDAndJobseekerID(long jobId, long jobseekerId);

}
