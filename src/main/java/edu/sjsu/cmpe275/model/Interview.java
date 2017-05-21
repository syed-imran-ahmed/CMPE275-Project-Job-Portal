package edu.sjsu.cmpe275.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Interview {
	@Id
	private String id;
	
	@Column(nullable = false)
	private String candidateName;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date interviewDate;
	
	@Column(nullable = false)
	private String place;
	
	@Column(nullable = false)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Interview(String id, String candidateName, Date interviewDate, String place, String status) {
		super();
		this.id = id;
		this.candidateName = candidateName;
		this.interviewDate = interviewDate;
		this.place = place;
		this.status = status;
	}
	
	public Interview(){}
	
}
