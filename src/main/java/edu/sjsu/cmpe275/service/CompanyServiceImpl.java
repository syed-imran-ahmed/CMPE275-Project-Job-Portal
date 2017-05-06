package edu.sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Company;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.repository.CompanyRepository;
import edu.sjsu.cmpe275.repository.UserRepository;

@Service
public class CompanyServiceImpl implements CompanyService{

	    @Autowired
	    private CompanyRepository companyRepository;

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Override
	    public Company findByCid(long Id) {
	        return companyRepository.findByCid(Id);
	    }

		@Override
		public void save(Company comp) {
	        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
	        User user = userRepository.findByUsername(currentUserName);
	        Company company =companyRepository.findByCid(user.getId());
	        if (company ==null)
	        	company = new Company();
	        company.setCid(comp.getCid());
	        company.setName(comp.getName());
	        company.setWebsite(comp.getWebsite());
	        company.setAddress(comp.getAddress());
	        company.setDescription(comp.getDescription());
	        company.setLogo(comp.getLogo());
	        company.setJobposition(comp.getJobposition());
	  
	        companyRepository.save(company);
		}
	}

