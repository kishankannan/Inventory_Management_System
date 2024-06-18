package com.jsp.wms.service;

import org.springframework.http.ResponseEntity;


import com.jsp.wms.requestdto.ClientRequest;
import com.jsp.wms.responsedto.ApiKeyResponse;
import com.jsp.wms.responsedto.ClientResponse;
import com.jsp.wms.util.ResponseStructure;

public interface ClientService {

	public ResponseEntity<ResponseStructure<ApiKeyResponse>> registerClient(ClientRequest clientRequest);

	public ResponseEntity<ResponseStructure<ClientResponse>> updateClient(ClientRequest clientRequest, int clientId);

}
