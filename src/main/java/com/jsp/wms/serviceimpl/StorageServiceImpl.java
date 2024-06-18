package com.jsp.wms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Storage;
import com.jsp.wms.entity.StorageType;
import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.exception.StorageNotFoundByIdException;
import com.jsp.wms.exception.StorageTypeNotFoundByIdException;
import com.jsp.wms.exception.WarehouseNotFoundByIdException;
import com.jsp.wms.mapper.StorageMapper;
import com.jsp.wms.mapper.StorageTypeMapper;
import com.jsp.wms.repository.StorageRepository;
import com.jsp.wms.repository.StorageTypeRepository;
import com.jsp.wms.repository.WareHouseRepository;
import com.jsp.wms.requestdto.StorageRequest;
import com.jsp.wms.requestdto.StorageTypeRequest;
import com.jsp.wms.responsedto.StorageResponse;
import com.jsp.wms.service.StorageService;
import com.jsp.wms.util.ResponseStructure;
import com.jsp.wms.util.SimpleStructure;

@Service
public class StorageServiceImpl implements StorageService{

	@Autowired
	private StorageRepository storageRepository;

	@Autowired
	private WareHouseRepository wareHouseRespository;

	@Autowired
	private StorageMapper storageMapper;
	
	@Autowired
	private StorageTypeRepository storageTypeRepository;
	
//	@Autowired
//	private StorageTypeRequest storageTypeRequest;

//	@Override
//	public ResponseEntity<SimpleStructure<String>> createStorage(StorageRequest storageRequest, int wareHouseId, int noOfStorageUnits) {
//
//		WareHouse wareHouse =  wareHouseRespository.findById(wareHouseId).orElseThrow(()-> new WarehouseNotFoundByIdException(""));
//
//		List<Storage> storages = new ArrayList<Storage>();
//
//		int count = 0;
//
//		while(noOfStorageUnits > 0) {
//
//			Storage storage  = storageMapper.mapToStorage(storageRequest, new Storage());
//
//			storage.setMaxAdditionalWeight(storageRequest.getCapacityInWeight());
//			storage.setAvailableArea(storageRequest.getLengthInMeters() * storageRequest.getBreadthInMeters() * storageRequest.getHeightInMeters());
//
//			wareHouse.setTotalCapacityInKg(storageRequest.getCapacityInWeight() * noOfStorageUnits + wareHouse.getTotalCapacityInKg());
//			storage.setWareHouse(wareHouse);
//
//			storages.add(storage);
//			count++;
//			noOfStorageUnits --;
//		}
//
//		storageRepository.saveAll(storages);
//
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(new SimpleStructure<String>()
//				.setStatus(HttpStatus.CREATED.value())
//				.setMesssage(""+count + " Storages created"));
//
//	}
	
	@Override
	public ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(int storageId, StorageRequest storageRequest) {
		
		return storageRepository.findById(storageId).map(existingStorage -> {
			
			existingStorage = storageMapper.mapToStorage(storageRequest, existingStorage);
			storageRepository.save(existingStorage);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StorageResponse>()
					.setStatus(HttpStatus.OK.value())
					.setMessage("Storage updated")
					.setData(storageMapper.mapToStorageResponse(existingStorage)));
					
			
		}).orElseThrow(()-> new StorageNotFoundByIdException("Storage Not Found"));
	}

//	@Override
//	public ResponseEntity<SimpleStructure<String>> createStorage(StorageRequest storageRequest, int wareHouseId,
//			int storageTypeId, int noOfStorageUnits) {
//		WareHouse wareHouse =  wareHouseRespository.findById(wareHouseId).orElseThrow(()-> new WarehouseNotFoundByIdException(""));
//		StorageType storageType = storageTypeRepository.findById(storageTypeId).orElseThrow(()-> new StorageTypeNotFoundByIdException(""));
//		
//		List<Storage> storages = new ArrayList<Storage>();
//
//		int count = 0;
//
//		while(noOfStorageUnits > 0) {
//
//			Storage storage  = storageMapper.mapToStorage(storageRequest, new Storage());
//
//			storage.setMaxAdditionalWeight(storageTypeRequest.getCapacityInWeight());
//			storage.setAvailableArea(storageTypeRequest.getLengthInMeters() * storageTypeRequest.getBreadthInMeters() * storageTypeRequest.getHeightInMeters());
//
//			wareHouse.setTotalCapacityInKg(storageTypeRequest.getCapacityInWeight() * noOfStorageUnits + wareHouse.getTotalCapacityInKg());
//			storageType.setUnitsAvailable(storageType.getUnitsAvailable()+noOfStorageUnits);
//			
//			storage.setWareHouse(wareHouse);
//
//			storages.add(storage);
//			count++;
//			noOfStorageUnits --;
//		}
//
//		storageRepository.saveAll(storages);
//
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(new SimpleStructure<String>()
//				.setStatus(HttpStatus.CREATED.value())
//				.setMesssage(""+count + " Storages created"));
//
//	}

//	@Override
//	public ResponseEntity<ResponseStructure<StorageResponse>> findByCapacityInKgAndLengthInMetersAndBreadthInMetersAndHeightInMeters(double capacityInWeight, double lengthInMeters, double breadthInMeters, double heightInMeters) {
//		
//		
//		return null;
//		
//	}


}
