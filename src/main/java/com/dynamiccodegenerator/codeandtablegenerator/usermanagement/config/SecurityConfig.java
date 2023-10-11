package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.filter.JwtAuthFilter;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service.UserInfoService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;
	
//	private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

	// User Creation
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoService();
	} // 
	private static final String[] AUTH_WHITELIST = {"/auth/welcome", "/auth/addNewUser","/auth/generateToken", "home"};
	// Configuring HttpSecurity
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http
//				.csrf(   http.csrf(home?error
//						httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()
//						)
//				.authorizeHttpRequests( 
//						auth -> auth
//	                    .requestMatchers(	AUTH_WHITELIST)
//	                    .permitAll()
//	                    .anyRequest().authenticated()
//	            )
//
//				.sessionManagement(
//						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//						)
//				 .headers(
//						 httpSecurityHeadersConfigurer -> 
//						 httpSecurityHeadersConfigurer.frameOptions(
//								 frameOptionsConfig -> frameOptionsConfig.disable()
//								 )) //to make accessible h2 console, it works as frame
//	            .exceptionHandling(
//	            		httpSecurityExceptionHandlingConfigurer -> 
//	            		httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
//	            				new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
////				.httpBasic(withDefaults())
//				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//				.build());
		
		return http.csrf(AbstractHttpConfigurer::disable)
		           .authorizeHttpRequests(request -> request.requestMatchers(AUTH_WHITELIST)
		                   .permitAll().anyRequest().authenticated())
		           .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		           .authenticationProvider(authenticationProvider()).addFilterBefore(
		        		   authFilter, UsernamePasswordAuthenticationFilter.class).build();

		
		
		
		
		// "/api/v1/auth/**"
//		return http.csrf(AbstractHttpConfigurer::disable)
//           .authorizeHttpRequests(request -> request.requestMatchers(AUTH_WHITELIST).permitAll()
//        		   .anyRequest().authenticated()
//        		   )
//           .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//           .authenticationProvider(authenticationProvider())
//           .addFilterBefore( authFilter, UsernamePasswordAuthenticationFilter.class)
//           
//           .formLogin((formLogin) ->
//           formLogin
//             // used for authenticating the credentials
////             .authenticationManager(authenticationManager)
//             // Custom persistence of the authentication
////             .securityContextRepository(securityContextRepository)
//             // expect a log in page at "/authenticate"
//             // a POST "/authenticate" is where authentication occurs
//             // error page at "/authenticate?error"
//             .loginPage("/home")
//             .loginProcessingUrl("/login")
//             .successHandler(jwtAuthenticationSuccessHandler)
//             .defaultSuccessUrl("/dashboard")
//             .failureUrl("/failure")
//             .permitAll()
//       )
//           .logout(    logout -> logout
//                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                   .permitAll()
//           )
//           .build();
//    http.build();
	}

	// Password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
