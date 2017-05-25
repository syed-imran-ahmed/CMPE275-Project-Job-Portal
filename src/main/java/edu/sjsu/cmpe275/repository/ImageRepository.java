package edu.sjsu.cmpe275.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	public Image findByJsid(long jsid);

}
