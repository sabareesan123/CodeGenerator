package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.JwtBlockList;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.JwtBlockListRepository;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.UserInfo;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.UserInfoRepository;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.resources.dtos.UserInfoDetails;




@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtBlockListRepository jwtBlockListRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userDetail = repository.findByName(username);

		// Converting userDetail to UserDetails
		return userDetail
				.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		repository.save(userInfo);
		return "User Added Successfully";
	}

	  public void invalidateJwt(String jwt)
	  {
		  JwtBlockList jwtBlockList = new JwtBlockList();
		  jwtBlockList.setJwt(jwt);
		  jwtBlockListRepository.save(jwtBlockList);
	  }
	  
	  

}
