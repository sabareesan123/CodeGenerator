package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.resources;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.UserInfo;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.resources.dtos.AuthRequest;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service.JwtService;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service.UserInfoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateToken")
	public ResponseEntity  authenticateAndGetToken(HttpServletResponse httpServletResponse,@RequestBody AuthRequest authRequest) {
       //ResponseEntity<Object>
		   
//		HttpHeaders headers = new HttpHeaders();
		  
//		return new ResponseEntity<String>(headers,HttpStatus.FOUND);
		    
//		      .body("Response with header using ResponseEntity");
		System.out.println(authRequest + "generate");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			System.out.println("authrnc 73 ");
			
			 ResponseCookie springCookie = ResponseCookie.from("jwt", jwtService.generateToken(authRequest.getUsername()))
					    .httpOnly(true)
//					    .secure(true)
					    .path("/")
					    .maxAge(600)
//					    .domain("example.com")
					    .build();
//			 Cookie cookie = new Cookie("jwt", jwtService.generateToken(authRequest.getUsername()));
//		        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
////		        cookie.setSecure(true);
//		        cookie.setHttpOnly(true);
//		        cookie.setPath("/"); // Global
//		        httpServletResponse.addCookie(cookie);
//		        return ResponseEntity.ok().build();
//			httpServletResponse.addHeader("x-token-jwt",jwtService.generateToken(authRequest.getUsername()) );
//			httpServletResponse.addHeader("Location", "templates/dashboard.html");  
//			 ModelAndView modelAndView = new ModelAndView();
//			 modelAndView.
//			    modelAndView.setViewName("dashboard");
		        return  ResponseEntity
		        .ok()
		        .header(HttpHeaders.SET_COOKIE, springCookie.toString())
		        .build();
			     
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}
	@GetMapping("/logout")
	@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
	public String logout(@CookieValue(name = "jwt",defaultValue = "jj") String jwt) {
		
		service.invalidateJwt(jwt);
		System.out.println("invalidated " + jwt);
		return "invalidated";
	}
}
