package edu.sjsu.cmpe275.model;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	

	@Column(name = "jobposition",nullable = true, unique = false)
	private String jobposition;
	
	@ManyToOne
	@JoinColumn(name = "company_cid")
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

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}
	
	public String getJobposition() {
		return jobposition;
	}

	public void setJobposition(String jobposition) {
		this.jobposition = jobposition;
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
	
	
	
	
	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public CompanyJobPosts(long jobid, String title, String jobposition, String desc, String resp, String loc, String sal,
			Company company) {
		super();
		this.jobid = jobid;
		this.title = title;
		this.descrip = desc;
		this.resp = resp;
		this.loc = loc;
		this.sal = sal;
		this.company = company;
		this.jobposition = jobposition;
	}

	public CompanyJobPosts(){}
	
	
}
