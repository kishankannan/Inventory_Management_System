package com.jsp.wms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Batch;
import com.jsp.wms.entity.Inventory;
import com.jsp.wms.entity.PurchaseOrder;
import com.jsp.wms.exception.InventoryNotFoundByIdException;
import com.jsp.wms.mapper.PurchaseOrderMapper;
import com.jsp.wms.repository.InventoryRepository;
import com.jsp.wms.repository.PurchaseOrderRepository;
import com.jsp.wms.requestdto.PurchaseOrderRequest;
import com.jsp.wms.responsedto.PurchaseOrderResponse;
import com.jsp.wms.service.PurchaseOrderService;
import com.jsp.wms.util.ResponseStructure;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private PurchaseOrderMapper purchaseOrderMapper;

	@Override
	public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> createPurchaseOrder(
			PurchaseOrderRequest purchaseOrderRequest, int productId) {

		Inventory inventory = inventoryRepository.findById(productId).orElseThrow(() -> new InventoryNotFoundByIdException("Inventory Not Found"));


		PurchaseOrder purchaseOrder = purchaseOrderMapper.mapToPurchaseOrder(purchaseOrderRequest, new PurchaseOrder());

		int oldQuantity = inventory.getBatchs().getFirst().getQuantity();

		if (oldQuantity >= purchaseOrderRequest.getOrderQuantity()) {

			int newQuantity = oldQuantity - purchaseOrderRequest.getOrderQuantity();
			Batch batch = inventory.getBatchs().getFirst();
			batch.setQuantity(newQuantity);

			inventory.getBatchs().add(batch);
			inventoryRepository.save(inventory);

			purchaseOrder.setInventories(List.of(inventory));
			purchaseOrder.setStatus("Active");

			purchaseOrderRepository.save(purchaseOrder);

		} 

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseStructure<PurchaseOrderResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("Purchase Order Created")
						.setData(purchaseOrderMapper.mapToPurchaseOrderResponse(purchaseOrder)));
	}

}
