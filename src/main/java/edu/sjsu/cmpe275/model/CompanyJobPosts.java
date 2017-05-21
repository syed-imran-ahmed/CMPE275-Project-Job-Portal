package edu.sjsu.cmpe275.model;
import java.lang.annotation.Repeatable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.Facets;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;



@Entity
@Indexed
@Table(name="company_job_posts")
public class CompanyJobPosts {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long jobid;
	
	@Column(name = "title",nullable = false, unique = false)
	@Fields( {
        @Field(name="job_title"),
       @Field(analyze = Analyze.NO, store = Store.YES)
      
        } )
	  @Facet
	private String title;
	
	@Column(name = "descrip",nullable = false, unique = false)
	@Field
	private String descrip;

	@Column(name = "resp",nullable = false, unique = false)
	@Field
	private String resp;
	
	
	@Column(name = "loc",nullable = false, unique = false)
	@Fields( {
        @Field(name="location"),
       @Field(analyze = Analyze.NO, store = Store.YES)
      
        } )
	  @Facet
	private String loc;
	
	@Column(name = "sal",nullable = false, unique = false)
	@Fields( {
        @Field(name="salary"),
       @Field(analyze = Analyze.NO, store = Store.YES)
      
        } )
	  @Facet
	private int sal;
	
	@Column(name = "jobposition",nullable = true, unique = false)
	private String jobposition;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_cid")
    private Company company;
	
	public Long getJobid() {
		return jobid;
	}

	public void setJobid(Long jobid) {
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

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
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

	public CompanyJobPosts(long jobid, String title, String jobposition, String desc, String resp, String loc, int sal,
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (jobid == null || obj == null || getClass() != obj.getClass())
			return false;
		CompanyJobPosts toCompare = (CompanyJobPosts) obj;
		return jobid.equals(toCompare.jobid);
	}

	@Override
	public int hashCode() {
		return jobid == null ? 0 : jobid.hashCode();
	}
	
	
}
