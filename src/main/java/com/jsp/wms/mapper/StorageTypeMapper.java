package com.jsp.wms.mapper;

import org.springframework.stereotype.Component;

import com.jsp.wms.entity.StorageType;
import com.jsp.wms.requestdto.StorageTypeRequest;
import com.jsp.wms.responsedto.StorageTypeResponse;

@Component
public class StorageTypeMapper {

	public StorageType mapToStorageType(StorageTypeRequest storageTypeRequest, StorageType storageType) {
		
		storageType.setLengthInMeters(storageTypeRequest.getLengthInMeters());
		storageType.setBreadthInMeters(storageTypeRequest.getBreadthInMeters());
		storageType.setHeightInMeters(storageTypeRequest.getHeightInMeters());
		storageType.setCapacityInWeight(storageTypeRequest.getCapacityInWeight());
		
		return storageType;
	}
	
	public StorageTypeResponse mapToStorageTypeResponse(StorageType storageType) {
		return StorageTypeResponse.builder()
				.storageTypeId(storageType.getStorageTypeId())
				.lengthInMeters(storageType.getLengthInMeters())
				.breadthInMeters(storageType.getBreadthInMeters())
				.heightInMeters(storageType.getHeightInMeters())
				.capacityInWeight(storageType.getCapacityInWeight())
				.unitsAvailable(storageType.getUnitsAvailable())
				.build();
	}
	
}
