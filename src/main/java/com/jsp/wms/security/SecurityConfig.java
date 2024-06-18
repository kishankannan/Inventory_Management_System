package com.jsp.wms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jsp.wms.filter.ApiKeyFilter;
import com.jsp.wms.repository.ClientRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private  ClientRepository clientRepository;
	@Bean
	PasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder(12);  

	}
	@Bean
	@Order(2)
	SecurityFilterChain securityFilterChain( HttpSecurity security) throws Exception {
		return security.csrf(csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers( "/api/v1/**", "/login/**") )
				.authorizeHttpRequests( authorize ->authorize.requestMatchers("/api/v1/register" ,"/login/**")
						.permitAll().anyRequest().authenticated())
				.formLogin( Customizer.withDefaults()).build(); 

	}
	@Bean
	@Order(1)
	SecurityFilterChain clientRequestFilterChain(HttpSecurity security ) throws Exception

	{
		return security.csrf( csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers( "/api/v1/client/**") )
				.authorizeHttpRequests( authorize-> authorize.anyRequest().permitAll() )
				.sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS))
				.addFilterBefore( new ApiKeyFilter(clientRepository), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}
