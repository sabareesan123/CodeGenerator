package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.resources;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home"; // This should match the name of your HTML file (without the .html extension)
    }
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public String showValidateFile() {

//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    	if( auth.getAuthorities().stream().filter( authority -> authority.getAuthority().equals("ROLE_ADMIN")).count() > 0 )
//    	{
//    		System.out.println("gran auth " +  auth.getAuthorities());
//    		   return "manager";
//    	
//    	}
        return "dashboard";
    }
    
    @GetMapping("/login")
    public String showLoginFile() {
        return "login"; 
    }
 
    
    @GetMapping("/details")
    public String showDetailsFile() {
        return "details"; 
    }
}
