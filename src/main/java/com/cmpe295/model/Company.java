package cmpe275;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Column(name = "HQadd",nullable = false, unique = false)
	private String hqadd;
	
	@Column(name = "descrip",nullable = true, unique = false)
	private String descrip;

	
	
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

	public String getHqadd() {
		return hqadd;
	}

	public void setHqadd(String hqadd) {
		this.hqadd = hqadd;
	}

	public String getDesc() {
		return descrip;
	}

	public void setDesc(String desc) {
		this.descrip = desc;
	}

	public Company(long cid, String name, String website, String logo, String hqadd, String desc) {
		super();
		this.cid = cid;
		this.name = name;
		this.website = website;
		this.logo = logo;
		this.hqadd = hqadd;
		this.descrip = desc;
	}
	public Company(){}
}
