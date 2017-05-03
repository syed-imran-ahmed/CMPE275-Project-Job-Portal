package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.Company;
public interface CompanyRepository  extends JpaRepository<Company, Long>{

	Company findByCid(long id);

}
