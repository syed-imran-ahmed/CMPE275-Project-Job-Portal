package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.model.Profile;
import edu.sjsu.cmpe275.model.User;

public interface UserService {
    void save(User user);
    
    void saveProfile(Profile profile);

    User findByUsername(String username);
    
    User findByEmailid(String emailid);
    
    User findById(long id);
    
    void saveUserVerification(String isVerified);
    
    boolean emailValidation(String emailid);
    
}
