package edu.sjsu.cmpe275.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.Interview;


public interface InterviewRepository  extends JpaRepository<Interview, Long>{
	
	public Interview findById(String id);

	public List<Interview> findByStatus(String status);
	
}
