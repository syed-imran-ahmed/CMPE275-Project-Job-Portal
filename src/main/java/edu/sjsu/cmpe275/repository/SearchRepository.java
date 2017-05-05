package edu.sjsu.cmpe275.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.CompanyJobPosts;
public interface SearchRepository  extends JpaRepository<CompanyJobPosts, Long> {
	
	}

