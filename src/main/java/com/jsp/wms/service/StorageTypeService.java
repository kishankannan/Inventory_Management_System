package com.jsp.wms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.requestdto.StorageTypeRequest;
import com.jsp.wms.responsedto.StorageResponse;
import com.jsp.wms.responsedto.StorageTypeResponse;
import com.jsp.wms.util.ResponseStructure;

public interface StorageTypeService {

	public ResponseEntity<ResponseStructure<StorageTypeResponse>> createStorageType(StorageTypeRequest storageTypeRequest);

	public ResponseEntity<ResponseStructure<StorageTypeResponse>> updateStorageType(StorageTypeRequest storageTypeRequest, int storageTypeId);

	public ResponseEntity<ResponseStructure<List<StorageTypeResponse>>> findAllStorageTypes();

}
