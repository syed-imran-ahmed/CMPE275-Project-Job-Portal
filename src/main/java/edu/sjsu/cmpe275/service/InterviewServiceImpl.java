package edu.sjsu.cmpe275.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Interview;
import edu.sjsu.cmpe275.repository.InterviewRepository;

@Service
public class InterviewServiceImpl implements InterviewService{

	@Autowired
	private InterviewRepository intrvw;
	
	@Override
	public Interview findById(String id){
		return intrvw.findById(id);
	}
	
	@Override
	public List<Interview> findByStatus(String status){
		return intrvw.findByStatus(status);
	}
	
	@Override
	public void save(Interview interview){
		Interview intw = new Interview (
				interview.getId(),
				interview.getCandidateName(),
				interview.getInterviewDate(),
				interview.getPlace(),
				interview.getStatus()
				);
		intrvw.save(intw);
	}
}
