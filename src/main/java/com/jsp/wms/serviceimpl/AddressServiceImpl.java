package com.jsp.wms.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Address;
import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.exception.AddressNotFoundByIdException;
import com.jsp.wms.exception.WarehouseNotFoundByIdException;
import com.jsp.wms.mapper.AddressMapper;
import com.jsp.wms.repository.AddressRepository;
import com.jsp.wms.repository.WareHouseRepository;
import com.jsp.wms.requestdto.AddressRequest;
import com.jsp.wms.responsedto.AddressResponse;
import com.jsp.wms.service.AddressService;
import com.jsp.wms.util.ResponseStructure;


@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private WareHouseRepository wareHouseRespository;

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(int wareHouseId, AddressRequest addressRequest) {

		WareHouse wareHouse = wareHouseRespository.findById(wareHouseId)
				.orElseThrow(() -> new  WarehouseNotFoundByIdException("WareHouse not Found"));


		Address address = addressRepository.save(addressMapper.mapToAddress(addressRequest, new Address()));
		address.setWareHouse(wareHouse);
		wareHouseRespository.save(wareHouse);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body( new ResponseStructure<AddressResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("Address added ")
						.setData(addressMapper.mapToAddressResponse(address)));
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(int addressId, AddressRequest addressRequest) {
		return addressRepository.findById(addressId).map(existingAddress -> {

			existingAddress = addressMapper.mapToAddress(addressRequest, existingAddress);

			Address address = addressRepository.save(existingAddress);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseStructure<AddressResponse>()
							.setStatus(HttpStatus.CREATED.value())
							.setMessage("Address updated ")
							.setData(addressMapper.mapToAddressResponse(address))
							);

		}).orElseThrow(() -> new AddressNotFoundByIdException("Address with ID not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(int addressId) {
		return addressRepository.findById(addressId).map(address -> {

			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ResponseStructure<AddressResponse>()
							.setStatus(HttpStatus.FOUND.value())
							.setMessage("Address found ")
							.setData(addressMapper.mapToAddressResponse(address)));

		}).orElseThrow(() -> new AddressNotFoundByIdException("Address with ID not found"));
	}

}
