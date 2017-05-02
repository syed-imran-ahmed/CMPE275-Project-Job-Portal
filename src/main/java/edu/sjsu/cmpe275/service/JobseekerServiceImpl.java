package edu.sjsu.cmpe275.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.repository.JobSeekerRepository;
import edu.sjsu.cmpe275.repository.UserRepository;

@Service
public class JobseekerServiceImpl implements JobseekerService{

	    @Autowired
	    private JobSeekerRepository JobSeekerRepository;

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Override
	    public JobSeeker findById(long Id) {
	        return JobSeekerRepository.findById(Id);
	    }

		@Override
		public void save(JobSeeker js) {
	        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userRepository.findByUsername(currentUserName);
	        JobSeeker j =JobSeekerRepository.findById(user.getId());
	        if (j ==null)
	        	j= new JobSeeker();
	        j.setId(js.getId());
	        j.setEducation(js.getEducation());
	        j.setFirstname(js.getFirstname());
	        j.setIntroduction(js.getIntroduction());
	        j.setJobs(js.getJobs());
	        j.setLastname(js.getLastname());
	        j.setPicture(js.getPicture());
	        j.setSkills(js.getSkills());
	        j.setWrk_exp(js.getWrk_exp());
	        JobSeekerRepository.save(j);
		}
	}

