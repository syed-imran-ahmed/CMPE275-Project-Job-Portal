package edu.sjsu.cmpe275.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CompanyJobPosts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long jobid;
	
	@Column(name = "title",nullable = false, unique = false)
	private String title;
	
	@Column(name = "descrip",nullable = false, unique = false)
	private String descrip;
	
	@Column(name = "resp",nullable = false, unique = false)
	private String resp;

	@Column(name = "loc",nullable = false, unique = false)
	private String loc;
	
	@Column(name = "sal",nullable = false, unique = false)
	private String sal;

	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "Company_ID")
    private Company company;
	
	public long getJobid() {
		return jobid;
	}

	public void setJobid(long jobid) {
		this.jobid = jobid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return descrip;
	}

	public void setDesc(String desc) {
		this.descrip = desc;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyJobPosts(long jobid, String title, String desc, String resp, String loc, String sal,
			Company company) {
		super();
		this.jobid = jobid;
		this.title = title;
		this.descrip = desc;
		this.resp = resp;
		this.loc = loc;
		this.sal = sal;
		this.company = company;
	}

	public CompanyJobPosts(){}
	
	
}
