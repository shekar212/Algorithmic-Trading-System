package com.example.demo.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.UserDAO;
import com.example.demo.Entity.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserDAO userDao;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user1=userDao.findByEmailAndPasswordNotNull(email);
		
		User user2=new User(user1.getEmail(),user1.getPassword(),new ArrayList<>());
		return user2;
    }

}
