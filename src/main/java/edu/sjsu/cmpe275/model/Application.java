package edu.sjsu.cmpe275.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Application {
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private long jobseekerID;
	
	@Column(nullable = false)
	private long jobID;
	
	@Column(nullable = false)
	private String jobseekerName;
	
	@Column(nullable = false)
	private String jobseekerEmail;
	
	@Column(nullable = true)
	private String jobseekerResumeLoc;

	@Column(nullable = false)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getJobseekerID() {
		return jobseekerID;
	}

	public void setJobseekerID(long jobseekerID) {
		this.jobseekerID = jobseekerID;
	}

	public long getJobID() {
		return jobID;
	}

	public void setJobID(long jobID) {
		this.jobID = jobID;
	}

	public String getJobseekerName() {
		return jobseekerName;
	}

	public void setJobseekerName(String jobseekerName) {
		this.jobseekerName = jobseekerName;
	}

	public String getJobseekerEmail() {
		return jobseekerEmail;
	}

	public void setJobseekerEmail(String jobseekerEmail) {
		this.jobseekerEmail = jobseekerEmail;
	}

	public String getJobseekerResumeLoc() {
		return jobseekerResumeLoc;
	}

	public void setJobseekerResumeLoc(String jobseekerResumeLoc) {
		this.jobseekerResumeLoc = jobseekerResumeLoc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Application(String id, long jobseekerID, long jobID, String jobseekerName, String jobseekerEmail,
			String jobseekerResumeLoc, String status) {
		super();
		this.id = id;
		this.jobseekerID = jobseekerID;
		this.jobID = jobID;
		this.jobseekerName = jobseekerName;
		this.jobseekerEmail = jobseekerEmail;
		this.jobseekerResumeLoc = jobseekerResumeLoc;
		this.status = status;
	}
	
	public Application(){}
	
	
}
