package com.jsp.wms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.wms.requestdto.InventoryRequest;
import com.jsp.wms.responsedto.InventoryResponse;
import com.jsp.wms.service.InventoryService;
import com.jsp.wms.util.ResponseStructure;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@PostMapping("/inventories/storages/{storageId}")
	public ResponseEntity<ResponseStructure<InventoryResponse>> createInventory(@RequestBody InventoryRequest inventoryRequest ,@PathVariable int storageId, @RequestParam int quantity){
		return inventoryService.createInventory(inventoryRequest ,storageId, quantity);
	}
	
	@GetMapping("/clients/{productId}/inventories")
	public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(@PathVariable int productId){
		return inventoryService.findInventory(productId);
	}
	
	@GetMapping("/inventories")
	public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findAllInventories(){
		return inventoryService.findAllInventories();
	}
	
	@PutMapping("/inventories/{productId}/storages/{storageId}")
	public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(@PathVariable int productId ,@PathVariable int storageId, @RequestParam int quantity){
		return inventoryService.updateInventory(productId , storageId , quantity);
	}
	
	
	@PutMapping("/inventories/{productId}")
	public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(@RequestBody InventoryRequest inventoryRequest,@PathVariable int productId ,@PathVariable int storageId){
		return inventoryService.updateInventory(inventoryRequest, productId , storageId);
	}
	
}
