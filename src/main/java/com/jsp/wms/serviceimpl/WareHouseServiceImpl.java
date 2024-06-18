package com.jsp.wms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.exception.WareHouseNotFoundByCityException;
import com.jsp.wms.exception.WarehouseNotFoundByIdException;
import com.jsp.wms.mapper.WareHouseMapper;
import com.jsp.wms.repository.AddressRepository;
import com.jsp.wms.repository.WareHouseRepository;
import com.jsp.wms.requestdto.WareHouseRequest;
import com.jsp.wms.responsedto.WareHouseResponse;
import com.jsp.wms.service.WareHouseService;
import com.jsp.wms.util.ResponseStructure;
@Service
public class WareHouseServiceImpl implements WareHouseService{

	@Autowired
	private WareHouseRepository wareHouseRepository;
	@Autowired
	private WareHouseMapper wareHouseMapper;
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public ResponseEntity<ResponseStructure<WareHouseResponse>> createWareHouse(WareHouseRequest wareHouseRequest) {
		WareHouse wareHouse = wareHouseRepository.save(wareHouseMapper.mapToWareHouse(wareHouseRequest, new WareHouse()));

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseStructure<WareHouseResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("WareHouse Created")
						.setData(wareHouseMapper.mapToWareHouseResponse(wareHouse)));
	}

	@Override
	public ResponseEntity<ResponseStructure<WareHouseResponse>> updateWareHouse(WareHouseRequest wareHouseRequest,
			int wareHouseId) {

		    return	wareHouseRepository.findById(wareHouseId).map(exWarehouse -> {

			exWarehouse = wareHouseMapper.mapToWareHouse(wareHouseRequest, exWarehouse);

			WareHouse wareHouse = wareHouseRepository.save(exWarehouse);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<WareHouseResponse>()
							.setStatus(HttpStatus.OK.value())
							.setMessage("Warehouse Updated")
							.setData(wareHouseMapper.mapToWareHouseResponse(wareHouse)));
		}).orElseThrow(()-> new WarehouseNotFoundByIdException("Warehouse Not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<WareHouseResponse>> findWareHouse(int wareHouseId) {
		
		return wareHouseRepository.findById(wareHouseId).map(wareHouse ->{

			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ResponseStructure<WareHouseResponse>().
							setStatus(HttpStatus.FOUND.value())
							.setMessage("WareHouse found")
							.setData(wareHouseMapper.mapToWareHouseResponse(wareHouse)));
		}).orElseThrow(()-> new WarehouseNotFoundByIdException("WareHouse Not Found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<List<WareHouseResponse>>> findWareHouses() {
		List<WareHouseResponse> wareHouseResponses = wareHouseRepository.findAll()
	            .stream()
	            .map(wareHouse -> wareHouseMapper.mapToWareHouseResponse(wareHouse))
	            .toList();

	    
	    return ResponseEntity.status(HttpStatus.FOUND)
	            .body(new ResponseStructure<List<WareHouseResponse>>()
	                    .setStatus(HttpStatus.FOUND.value())
	                    .setMessage("WareHouse Found")
	                    .setData(wareHouseResponses));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<WareHouseResponse>>> findWarehousesByCity(String city) {
		List<WareHouseResponse> warehouses = addressRepository.findWarehousesByCity(city)
				.stream()
				.map(address -> wareHouseMapper.mapToWareHouseAddress(address, address.getWareHouse()))
				.toList();
				
		if(warehouses.isEmpty())
			throw new WareHouseNotFoundByCityException("Warehouse Not Found");
			
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ResponseStructure<List<WareHouseResponse>>()
							.setStatus(HttpStatus.FOUND.value())
							.setMessage("Warehouse Found By Cities")
							.setData(warehouses));
	}


}
