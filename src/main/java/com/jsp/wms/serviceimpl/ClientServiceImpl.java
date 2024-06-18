package com.jsp.wms.serviceimpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Client;
import com.jsp.wms.exception.ClientNotFoundByIdException;
import com.jsp.wms.mapper.ClientMapper;
import com.jsp.wms.repository.ClientRepository;
import com.jsp.wms.requestdto.ClientRequest;
import com.jsp.wms.responsedto.ApiKeyResponse;
import com.jsp.wms.responsedto.ClientResponse;
import com.jsp.wms.service.ClientService;
import com.jsp.wms.util.ResponseStructure;


@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientMapper clientMapper;

	@Override
	public ResponseEntity<ResponseStructure<ApiKeyResponse>> registerClient(ClientRequest clientRequest) {

		String apiKey = UUID.randomUUID().toString();

		Client client = clientMapper.mapToClientRequest(clientRequest, new Client());
		client.setApiKey(apiKey);

		client = clientRepository.save(client);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseStructure<ApiKeyResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("Client Created")
						.setData(clientMapper.mapToApiKeyResponse(client)));


	}

	@Override
	public ResponseEntity<ResponseStructure<ClientResponse>> updateClient(ClientRequest clientRequest, int clientId) {
		
		return clientRepository.findById(clientId).map(exClient -> {
			exClient = clientMapper.mapToClientRequest(clientRequest, exClient);
			
			 Client client = clientRepository.save(exClient);
			 
			return ResponseEntity.status(HttpStatus.OK)
			 .body(new ResponseStructure<ClientResponse>()
					 .setStatus(HttpStatus.OK.value())
					 .setMessage("Client Updated")
					 .setData(clientMapper.mapToClientResponse(client)));
		}).orElseThrow(()-> new ClientNotFoundByIdException("Client Not Found"));
	
	}
	
}
