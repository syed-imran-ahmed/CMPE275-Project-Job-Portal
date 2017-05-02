package edu.sjsu.cmpe275.service;
import edu.sjsu.cmpe275.model.JobSeeker;

public interface JobseekerService {
	    void save(JobSeeker js);
	    
	    JobSeeker findById(long id);

}
