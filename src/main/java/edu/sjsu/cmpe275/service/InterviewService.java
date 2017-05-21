package edu.sjsu.cmpe275.service;

import java.util.List;

import edu.sjsu.cmpe275.model.Interview;

public interface InterviewService {
	
	 void save(Interview interview);
	    
	 Interview findById(String jobAppId);
	 
	 List<Interview> findByStatus(String status);
}
