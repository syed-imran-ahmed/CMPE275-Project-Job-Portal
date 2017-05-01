package edu.sjsu.cmpe275.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class JobSeeker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false, unique = true)
	private long id;
	
	@Column(name = "first_name",nullable = false, unique = false)
	private String fistname;
	
	@Column(name = "last_name",nullable = false, unique = false)
	private String lastname;
	
	@Column(name = "picture",nullable = true, unique = false)
	private byte[] picture;
	
	@Column(name = "intro",nullable = true, unique = false)
	private String introduction;
	
	@Column(name = "wrk_exp",nullable = false, unique = false)
	private String wrk_exp;
	
	@Column(name = "edu",nullable = false, unique = false)
	private String education;
	
	@Column(name = "skill",nullable = false, unique = false)
	private String skills;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinTable(
			name="T_Jobseeker_JOB",
			joinColumns={@JoinColumn(name="JobseekerID", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="JobID", referencedColumnName="jobid")})
	private List< CompanyJobPosts > jobs= new ArrayList<>();

	public JobSeeker(long id, String fistname, String lastname, byte[] picture, String introduction, String wrk_exp,
			String education, String skills, List<CompanyJobPosts> jobs) {
		super();
		this.id = id;
		this.fistname = fistname;
		this.lastname = lastname;
		this.picture = picture;
		this.introduction = introduction;
		this.wrk_exp = wrk_exp;
		this.education = education;
		this.skills = skills;
		this.jobs =jobs;
	}

	public JobSeeker(){}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFistname() {
		return fistname;
	}

	public void setFistname(String fistname) {
		this.fistname = fistname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getWrk_exp() {
		return wrk_exp;
	}

	public void setWrk_exp(String wrk_exp) {
		this.wrk_exp = wrk_exp;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public List<CompanyJobPosts> getJobs() {
		return jobs;
	}

	public void setJobs(List<CompanyJobPosts> jobs) {
		this.jobs = jobs;
	}
}
