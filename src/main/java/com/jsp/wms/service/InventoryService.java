package com.jsp.wms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.requestdto.InventoryRequest;
import com.jsp.wms.responsedto.InventoryResponse;
import com.jsp.wms.util.ResponseStructure;

public interface InventoryService {

	public ResponseEntity<ResponseStructure<InventoryResponse>> createInventory(InventoryRequest inventoryRequest,
			int storageId, int quantity);

	public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findAllInventories();

	public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(int productId);

	public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(int productId,int storageId, int quantity);

	public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(InventoryRequest inventoryRequest,
			int productId, int storageId);

}
