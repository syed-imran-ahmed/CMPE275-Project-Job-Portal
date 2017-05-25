package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.model.Image;

public interface ImageService {

	void save(Image image);
	String findByJsid(long jsid);
}
