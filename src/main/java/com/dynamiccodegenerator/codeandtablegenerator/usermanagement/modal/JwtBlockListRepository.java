package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JwtBlockListRepository extends JpaRepository<JwtBlockList, Integer>{
	
	JwtBlockList findByJwt(String jwt);

}
