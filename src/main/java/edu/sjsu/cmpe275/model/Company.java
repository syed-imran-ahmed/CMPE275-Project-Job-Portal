package edu.sjsu.cmpe275.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cid;
	
	@Column(name = "name",nullable = false, unique = false)
	private String name;
	
	@Column(name = "website",nullable = false, unique = false)
	private String website;
	
	@Column(name = "logo",nullable = true, unique = false)
	private String logo;

	@Column(name = "address",nullable = false, unique = false)
	private String address;
	
	@Column(name = "description",nullable = true, unique = false)
	private String description;
	
	@Column(name = "jobposition",nullable = true, unique = false)
	private String jobposition;
	
	
	
	@OneToMany(orphanRemoval=true, mappedBy = "company", cascade = CascadeType.ALL)
	private List<CompanyJobPosts> jobPosts;

	public List<CompanyJobPosts> getJobPosts() {
		return jobPosts;
	}

	public void setJobPosts(List<CompanyJobPosts> jobPosts) {
		this.jobPosts = jobPosts;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}
	
	public String getJobposition() {
		return jobposition;
	}

	public void setJobposition(String jobposition) {
		this.jobposition = jobposition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company(long cid, String name, String website, String logo, String address, String description, String jobposition) {
		super();
		this.cid = cid;
		this.name = name;
		this.website = website;
		this.logo = logo;
		this.address = address;
		this.description = description;
		this.jobposition = jobposition;
	}
	public Company(){}
}
