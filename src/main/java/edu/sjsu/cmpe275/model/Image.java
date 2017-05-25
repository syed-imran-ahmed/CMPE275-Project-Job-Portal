package edu.sjsu.cmpe275.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Image {
	@Id
	private long jsid;
	
	@Column
	private String imagename;

	public long getJsid() {
		return jsid;
	}

	public void setJsid(long jsid) {
		this.jsid = jsid;
	}

	public String getImagename() {
		return imagename;
	}
	

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public Image(){}
	
	public Image(long jsid, String imagename) {
		super();
		this.jsid = jsid;
		this.imagename = imagename;
	}

	
}
