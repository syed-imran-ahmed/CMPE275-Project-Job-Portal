package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.CompanyJobPosts;
public interface CompanyJobsRepository  extends JpaRepository<CompanyJobPosts, Long>{

	CompanyJobPosts findByJobid(long id);

}
