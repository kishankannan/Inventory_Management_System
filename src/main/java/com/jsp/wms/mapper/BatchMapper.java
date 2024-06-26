package com.jsp.wms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsp.wms.entity.Batch;
import com.jsp.wms.responsedto.BatchResponse;

@Component
public class BatchMapper {

	@Autowired
	private StorageMapper storageMapper;
	
	public BatchResponse mapToBatchResponse(Batch batch) {
		
		return BatchResponse.builder()
				.batchId(batch.getBatchId())
				.quantity(batch.getQuantity())
				.storage(storageMapper.mapToStorageResponse(batch.getStorage()))
				.build();
	}
}
