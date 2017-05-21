package edu.sjsu.cmpe275.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Interested;
import edu.sjsu.cmpe275.repository.InterestedRepository;
@Service
public class InterestedServiceImpl implements InterestedService {
	
	@Autowired
	private InterestedRepository intrstdrepo;
	
	@Override
	public void save(Interested interested){
		Interested interstd= new Interested(
				interested.isStatus(),
				interested.getId(),
				interested.getJobseekerid(),
				interested.getJobid()
				);
		intrstdrepo.save(interstd);
	}
	
	@Override
	public void removeByJobid(long jobid){
		intrstdrepo.removeByJobid(jobid);
	}
	
	@Override
	public void removeById(String id){
		intrstdrepo.removeById(id);
	}
	
	@Override
	public Interested findById(String id){
		return intrstdrepo.findById(id);
	}
	
	@Override
	public List<Interested> findByJobseekerid(long jobseekerid){
		return intrstdrepo.findByJobseekerid(jobseekerid);
	}
	
	@Override
	public List<Interested> findByJobid(long jobid){
		return intrstdrepo.findByJobid(jobid);
	}
}
