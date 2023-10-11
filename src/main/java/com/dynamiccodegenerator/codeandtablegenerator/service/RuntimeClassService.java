package com.dynamiccodegenerator.codeandtablegenerator.service;

import org.springframework.stereotype.Service;

import com.dynamiccodegenerator.codeandtablegenerator.model.RuntimeClass;
import com.dynamiccodegenerator.codeandtablegenerator.model.RuntimeClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RuntimeClassService {

	private final RuntimeClassRepository runtimeClassRepository;
	
	RuntimeClass  get(int classId)
	 {
		return runtimeClassRepository.findById(classId).get();
		
	    	  
	 }
}
