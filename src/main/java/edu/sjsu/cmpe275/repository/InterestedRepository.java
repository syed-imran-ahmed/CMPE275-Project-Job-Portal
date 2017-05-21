package edu.sjsu.cmpe275.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.sjsu.cmpe275.model.Interested;

public interface InterestedRepository extends JpaRepository<Interested, String>{
	Interested findById(String id);
	List<Interested> findByJobseekerid(long jobseekerid);
	List<Interested> findByJobid(long jobid);
	
	@Modifying
    @Transactional
    @Query("delete from Interested i where i.jobid = ?1")
	void removeByJobid(long jobid);
	
	@Modifying
    @Transactional
    @Query("delete from Interested i where i.id = ?1")
	void removeById(String id);
}
