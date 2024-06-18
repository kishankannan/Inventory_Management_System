package com.jsp.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.wms.requestdto.AddressRequest;
import com.jsp.wms.responsedto.AddressResponse;
import com.jsp.wms.service.AddressService;
import com.jsp.wms.util.ResponseStructure;

@RestController
@RequestMapping("/api/v1")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PreAuthorize("hasAuthority('CREATE_ADDRESS')")
	@PostMapping("/warehouses/{wareHouseId}/addresses")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@PathVariable int wareHouseId,@RequestBody AddressRequest addressRequest) {
		return addressService.addAddress(wareHouseId,addressRequest);
	}


	@PutMapping("/addresses/{addressId}")
	@PreAuthorize("hasAuthority('UPDATE_ADDRESS')")
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@PathVariable int addressId,
			@RequestBody AddressRequest addressRequest) {
		return addressService.updateAddress(addressId,addressRequest);
	}

	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(@PathVariable int addressId) {
		return addressService.findAddress(addressId);
	}


}

