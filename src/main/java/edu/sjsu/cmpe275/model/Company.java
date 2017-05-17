package edu.sjsu.cmpe275.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name="company")
public class Company {
	@Id
	private long cid;
	
	@Column(name = "name",nullable = false, unique = false)
	@Field(analyze = Analyze.NO, store = Store.NO)
	@Facet
	private String name;
	
	@Column(name = "website",nullable = false, unique = false)
	private String website;
	
	@Column(name = "logo",nullable = true, unique = false)
	private String logo;

	@Column(name = "address",nullable = false, unique = false)
	private String address;
	
	@Column(name = "description",nullable = true, unique = false)
	private String description;
	


	
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
	}
	public Company(){}
}
