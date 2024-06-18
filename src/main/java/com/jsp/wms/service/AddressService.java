package com.jsp.wms.service;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.requestdto.AddressRequest;
import com.jsp.wms.responsedto.AddressResponse;
import com.jsp.wms.util.ResponseStructure;

public interface AddressService {

	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(int wareHouseId, AddressRequest addressRequest);

	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(int addressId, AddressRequest addressRequest);

	public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(int addressId);

}
