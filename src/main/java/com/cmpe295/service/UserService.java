package com.cmpe295.service;

import com.cmpe295.model.Profile;
import com.cmpe295.model.User;

public interface UserService {
    void save(User user);
    
    void saveProfile(Profile profile);

    User findByUsername(String username);
}
