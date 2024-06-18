package com.jsp.wms.service;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.requestdto.StorageRequest;
import com.jsp.wms.responsedto.StorageResponse;
import com.jsp.wms.util.ResponseStructure;
import com.jsp.wms.util.SimpleStructure;

public interface StorageService {

	//public ResponseEntity<SimpleStructure<String>> createStorage(StorageRequest storageRequest, int wareHouseId, int noOfStorageUnits);

	public ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(int storageId, StorageRequest storageRequest);

//	public ResponseEntity<SimpleStructure<String>> createStorage(StorageRequest storageRequest, int wareHouseId,
//			int storageTypeId, int noOfStorageUnits);

//	public ResponseEntity<ResponseStructure<StorageResponse>> findByCapacityInKgAndLengthInMetersAndBreadthInMetersAndHeightInMeters(double capacityInWeight, double lengthInMeters, double breadthInMeters, double heightInMeters);

}
