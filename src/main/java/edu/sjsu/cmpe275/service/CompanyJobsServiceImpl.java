package edu.sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.repository.CompanyJobsRepository;

@Service
public class CompanyJobsServiceImpl implements CompanyJobsService{

	    @Autowired
	    private CompanyJobsRepository companyJobsRepository;
	    
	    @Override
	    public CompanyJobPosts findByJobId(long Id) {
	        return companyJobsRepository.findByJobid(Id);
	    }

		@Override
		public void save(CompanyJobPosts jobPost) {
	       
	        CompanyJobPosts companyJobPost = new CompanyJobPosts();
	        
	        companyJobPost.setTitle(jobPost.getTitle());
	        companyJobPost.setDescrip(jobPost.getDescrip());
	        companyJobPost.setLoc(jobPost.getLoc());
	        companyJobPost.setResp(jobPost.getResp());
	        companyJobPost.setSal(jobPost.getSal());
	        companyJobPost.setCompany(jobPost.getCompany());
	        companyJobPost.setJobposition(jobPost.getJobposition());
	  
	        companyJobsRepository.save(companyJobPost);
		}
	}

