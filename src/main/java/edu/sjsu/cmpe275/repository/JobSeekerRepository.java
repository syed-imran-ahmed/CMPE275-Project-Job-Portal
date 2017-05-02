package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.JobSeeker;
public interface JobSeekerRepository  extends JpaRepository<JobSeeker, Long>{

	JobSeeker findById(long id);

}
