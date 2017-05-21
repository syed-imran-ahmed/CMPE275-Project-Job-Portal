package edu.sjsu.cmpe275.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Interested {
	
	
	@Column(name = "status",nullable = false, unique = false)
	private boolean status;
	
	@Id
	@Column(name = "id",nullable = false, unique = true)
	private String id;
	
	@Column(name = "jobseekerid",nullable = false, unique = false)
	private long jobseekerid;
	
	@Column(name = "jobid",nullable = false, unique = false)
	private long jobid;

	public boolean isStatus() {
		return status;
	}

	public void setInterested(boolean status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getJobseekerid() {
		return jobseekerid;
	}

	public void setJobseekerid(long jobseekerid) {
		this.jobseekerid = jobseekerid;
	}

	public Interested(){}
	
	public Interested(boolean status, String id, long jobseekerid,long jobid) {
		super();
		this.status = status;
		this.id = id;
		this.jobseekerid = jobseekerid;
		this.jobid = jobid;
	}

	public long getJobid() {
		return jobid;
	}

	public void setJobid(long jobid) {
		this.jobid = jobid;
	}
}