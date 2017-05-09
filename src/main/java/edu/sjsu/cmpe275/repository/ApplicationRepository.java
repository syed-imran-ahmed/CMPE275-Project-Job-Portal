package edu.sjsu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.Application;


public interface ApplicationRepository  extends JpaRepository<Application, Long>{

	public Application findById(String id);
	
	public List<Application> findByjobID(long Id);
	
	public List<Application> findByjobseekerID(long Id);
}
