package edu.sjsu.cmpe275.service;

import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.model.Profile;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.repository.RoleRepository;
import edu.sjsu.cmpe275.repository.UserRepository;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public void saveProfile(Profile profile) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
		user.setProfile(profile);
		userRepository.save(user);
	}

	@Override
	public void saveUserVerification(String isVerified) {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        user.setIsVerified(isVerified);
		userRepository.save(user);		
	}

	@Override
	public User findByEmailid(String emailid) {
		return userRepository.findByEmailid(emailid);
	}
	
	@Override
	public boolean emailValidation(String emailid){

		String reg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		 
		Pattern pattern = Pattern.compile(reg);
	
		    Matcher matcher = pattern.matcher(emailid);
		    return matcher.matches();
		
	}	
	
}
