package edu.sjsu.cmpe275.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Application;
import edu.sjsu.cmpe275.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService{

	@Autowired
    private ApplicationRepository apprepo;
	
	@Override
	public Application findById(String Id){
		return apprepo.findById(Id);
	}
	
	@Override
	public List<Application> findByjobID(long Id){
		return apprepo.findByjobID(Id);
	}
	
	@Override
	public List<Application> findByjobseekerID(long Id){
		return apprepo.findByjobseekerID(Id);
	}
	
	@Override
	public void save(Application application){
		Application app = new Application(
				application.getId(),
				application.getJobseekerID(),
				application.getJobID(),
				application.getJobseekerName(),
				application.getJobseekerEmail(),
				application.getJobseekerResumeLoc(),
				application.getStatus()
				);
		apprepo.save(app);
	}
}
