package edu.sjsu.cmpe275.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Image;
import edu.sjsu.cmpe275.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService{

	@Autowired
    private ImageRepository imagerepository;
	
	@Override
	public String findByJsid(long jsid){
		if(imagerepository.findByJsid(jsid) != null){
			return imagerepository.findByJsid(jsid).getImagename();
		}
		else{
			return null;
		}
	}
	
	@Override
	public void save(Image image){
		Image img =new Image(
				image.getJsid(),
				image.getImagename()
				);
		
		imagerepository.save(img);
		
	}
}
