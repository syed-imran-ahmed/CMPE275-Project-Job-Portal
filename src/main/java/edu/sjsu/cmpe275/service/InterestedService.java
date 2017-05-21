package edu.sjsu.cmpe275.service;

import java.util.List;

import edu.sjsu.cmpe275.model.Interested;

public interface InterestedService {
	void removeByJobid(long jobid);
	void removeById(String id);
	void save(Interested interested);
	Interested findById(String id);
	List<Interested> findByJobseekerid(long jobseekerid);
	List<Interested> findByJobid(long jobid);
}
