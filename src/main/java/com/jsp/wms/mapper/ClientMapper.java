package com.jsp.wms.mapper;

import org.springframework.stereotype.Component;

import com.jsp.wms.entity.Client;
import com.jsp.wms.requestdto.ClientRequest;
import com.jsp.wms.responsedto.ApiKeyResponse;
import com.jsp.wms.responsedto.ClientResponse;

@Component
public class ClientMapper {

	public Client mapToClientRequest(ClientRequest clientRequest , Client client) {
		client.setBusinessName(clientRequest.getBusinessName());
		client.setEmail(clientRequest.getEmail());
		client.setContactNumber(clientRequest.getContactNumber());
		
		return client;
	}
	
	public ApiKeyResponse mapToApiKeyResponse(Client client) {
		return ApiKeyResponse.builder()
				.apiKey(client.getApiKey())
				.build();
	}
	
	public ClientResponse mapToClientResponse(Client client) {
		return ClientResponse.builder()
				.clientId(client.getClientId())
				.businessName(client.getBusinessName())
				.email(client.getEmail())
				.contactNumber(client.getContactNumber())
				.build();
	}
	
}
