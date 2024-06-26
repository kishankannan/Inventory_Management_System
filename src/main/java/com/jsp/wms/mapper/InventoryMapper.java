package com.jsp.wms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsp.wms.entity.Batch;
import com.jsp.wms.entity.Inventory;
import com.jsp.wms.requestdto.InventoryRequest;
import com.jsp.wms.responsedto.InventoryResponse;

@Component
public class InventoryMapper {
	
	@Autowired
	private BatchMapper batchMapper;

	public Inventory mapToInventory(InventoryRequest inventoryRequest, Inventory inventory) {

		inventory.setProductTitle(inventoryRequest.getProductTitle());
		inventory.setLengthInMeters(inventoryRequest.getLengthInMeters());
		inventory.setBreadthInMeters(inventoryRequest.getBreadthInMeters());
		inventory.setHeightInMeters(inventoryRequest.getHeightInMeters());
		inventory.setWeightInKg(inventoryRequest.getWeightInKg());
		inventory.setMaterialTypes(inventoryRequest.getMaterialTypes());
		inventory.setSellerId(inventoryRequest.getSellerId());

		return inventory;
	}

	public InventoryResponse mapToInventoryResponse(Inventory inventory) {

		return InventoryResponse.builder()
				.productId(inventory.getProductId())
				.productTitle(inventory.getProductTitle())
				.lengthInMeters(inventory.getLengthInMeters())
				.breadthInMeters(inventory.getBreadthInMeters())
				.heightInMeters(inventory.getHeightInMeters())
				.weightInKg(inventory.getWeightInKg())
				.materialTypes(inventory.getMaterialTypes())
				.restockedAt(inventory.getRestockedAt())
				.sellerId(inventory.getSellerId())
				.build();
	}

	public InventoryResponse mapToInventoryResponse(Inventory inventory , Batch batch) {

		return InventoryResponse.builder()
				.productId(inventory.getProductId())
				.productTitle(inventory.getProductTitle())
				.lengthInMeters(inventory.getLengthInMeters())
				.breadthInMeters(inventory.getBreadthInMeters())
				.heightInMeters(inventory.getHeightInMeters())
				.weightInKg(inventory.getWeightInKg())
				.materialTypes(inventory.getMaterialTypes())
				.restockedAt(inventory.getRestockedAt())
				.sellerId(inventory.getSellerId())
				.batch(batchMapper.mapToBatchResponse(batch))
				.build();

	}

}
