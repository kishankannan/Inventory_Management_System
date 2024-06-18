package com.jsp.wms.filter;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.wms.entity.Client;
import com.jsp.wms.exception.IllegalOperationException;
import com.jsp.wms.repository.ClientRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiKeyFilter extends OncePerRequestFilter {

	public ApiKeyFilter(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	private ClientRepository clientRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(request.getSession(false) != null) {
			throw new IllegalOperationException("Cannot create an client");
		}
		
		if(! request.getRequestURI().equals("/api/v1/client/register")) {

			String   username=  request.getHeader( "USERNAME");
			String    apikey=  request.getHeader( "API-KEY");
			if ( username!=null &&apikey!=null )
			{
				Client  client= clientRepository.findByEmail(username)
						.orElseThrow( () -> new  UsernameNotFoundException(" User not found") ); 
				if ( !apikey.equals(client.getApiKey())) 
					throw  new BadCredentialsException( "Invalid Credential");

			}
			else  {
				throw new UsernameNotFoundException( "not found");

			}
		}
		filterChain.doFilter(request, response);

	}

}