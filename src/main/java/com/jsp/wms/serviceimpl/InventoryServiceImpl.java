package com.jsp.wms.serviceimpl;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Batch;
import com.jsp.wms.entity.Inventory;
import com.jsp.wms.entity.Storage;
import com.jsp.wms.exception.BatchNotFoundByIdException;
import com.jsp.wms.exception.InventoryNotFoundByIdException;
import com.jsp.wms.exception.SpaceOrWeightNotAvailableException;
import com.jsp.wms.exception.StorageNotFoundByIdException;
import com.jsp.wms.mapper.InventoryMapper;
import com.jsp.wms.repository.BatchRepository;
import com.jsp.wms.repository.InventoryRepository;
import com.jsp.wms.repository.StorageRepository;
import com.jsp.wms.requestdto.InventoryRequest;
import com.jsp.wms.responsedto.InventoryResponse;
import com.jsp.wms.service.InventoryService;
import com.jsp.wms.util.ResponseStructure;

@Service
public class InventoryServiceImpl implements InventoryService{

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private StorageRepository storageRepository;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	
	private Batch batch;
	
	@Autowired
	private BatchRepository batchRepository;
	
		
		@Override
		public ResponseEntity<ResponseStructure<InventoryResponse>> createInventory(InventoryRequest inventoryRequest , int storageId, int quantity) {
			
			Storage storage = storageRepository.findById(storageId).orElseThrow(() -> new StorageNotFoundByIdException("Storage Not Found"));

			Inventory inventory = inventoryMapper.mapToInventory(inventoryRequest, new Inventory());
			inventory.setRestockedAt(LocalDate.now());

			storage.setMaxAdditionalWeight(storage.getMaxAdditionalWeight() - inventoryRequest.getWeightInKg()* quantity);
			storage.setAvailableArea(inventoryRequest.getLengthInMeters()*inventoryRequest.getBreadthInMeters()*inventoryRequest.getHeightInMeters());

			Batch batch = new Batch();
			
			batch.setInventory(inventory);
			batch.setStorage(storage);
			batch.setQuantity(quantity);

			storageRepository.save(storage);
			inventory = inventoryRepository.save(inventory);
			batch = batchRepository.save(batch);


			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseStructure<InventoryResponse>()
							.setStatus(HttpStatus.CREATED.value())
							.setMessage("Inventory Created")
							.setData(inventoryMapper.mapToInventoryResponse(inventory , batch)));
		}


		@Override
		public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findAllInventories() {
			List<InventoryResponse> inventoryResponses = inventoryRepository.findAll()
					.stream()
					.map(inventories -> inventoryMapper.mapToInventoryResponse(inventories))
					.toList();

			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ResponseStructure<List<InventoryResponse>>()
							.setStatus(HttpStatus.FOUND.value())
							.setMessage("Inventories found")
							.setData(inventoryResponses)
							);
		}


		@Override
		public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(int productId) {
			
			return inventoryRepository.findById(productId).map(inventory -> {
				
				return ResponseEntity.status(HttpStatus.FOUND)
						.body(new ResponseStructure<InventoryResponse>()
								.setStatus(HttpStatus.FOUND.value())
								.setMessage("Inventory Found")
								.setData(inventoryMapper.mapToInventoryResponse(inventory)));
			}).orElseThrow(()-> new InventoryNotFoundByIdException("Inventory not found"));
		}


		@Override
		public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(int productId, int storageId, int quantity) {
			
			Inventory inventory = inventoryRepository.findById(productId).orElseThrow(()-> new InventoryNotFoundByIdException("Inventory Not Found"));
			Storage storage = storageRepository.findById(storageId).orElseThrow(() -> new StorageNotFoundByIdException("Storage Not Found"));

			List<Batch> batches = batchRepository.findByInventoryAndStorage(inventory, storage);

			Batch batch = new Batch();
			batch.setQuantity(quantity);
			batch.setInventory(inventory);
			batch.setStorage(storage);

			batchRepository.save(batch);
			inventoryRepository.save(inventory);
			storageRepository.save(storage);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<InventoryResponse>()
							.setStatus(HttpStatus.OK.value())
							.setMessage("Quantity Updated")
							.setData(inventoryMapper.mapToInventoryResponse(inventory, batch))
							);
		}
		
		@Override
		public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(InventoryRequest inventoryRequest, int productId, int storageId) {
		
			return inventoryRepository.findById(productId).map(inventory -> {
				
				Batch batch = batchRepository.findByInventoryProductId(productId).orElseThrow(() -> new BatchNotFoundByIdException("Stock not found"));
				
					int oldQuantity = batch.getQuantity();
					double oldLength = inventory.getLengthInMeters();
					double oldBreadth = inventory.getBreadthInMeters();
					double oldHeight = inventory.getHeightInMeters();
					
				 double originalWeight = inventory.getWeightInKg() * oldQuantity;
				 double originalArea = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
				 
				 Storage storage = batch.getStorage();
				 
				Inventory updatedInventory = inventoryMapper.mapToInventory(inventoryRequest, inventory);
				 

				   double newWeight = updatedInventory.getWeightInKg() * oldQuantity;
				   double newArea = updatedInventory.getBreadthInMeters() * updatedInventory.getHeightInMeters() * updatedInventory.getLengthInMeters();
				   
				  if((oldLength != updatedInventory.getLengthInMeters() || oldBreadth != updatedInventory.getBreadthInMeters() || oldHeight != updatedInventory.getHeightInMeters())
						  || originalWeight != newWeight)
						  {
									if(storage.getAvailableArea()>0 && storage.getMaxAdditionalWeight() >0)
									{
										storage.setMaxAdditionalWeight(storage.getMaxAdditionalWeight() + originalWeight - newWeight);
								        storage.setAvailableArea(storage.getAvailableArea() + originalArea - newArea);  
									}
									
									else
									{
										throw new SpaceOrWeightNotAvailableException("No Available Area or Capacity of Storage Full");
									}

						  }
				  
				  	updatedInventory = inventoryRepository.save(updatedInventory);
					batch.setInventory(updatedInventory);
					batch.setStorage(storage);
					batchRepository.save(batch); 
					storageRepository.save(storage);
		
			    return ResponseEntity.status(HttpStatus.OK)
			            .body(new ResponseStructure<InventoryResponse>()
			                    .setData(inventoryMapper.mapToInventoryResponse(updatedInventory, batch))
			                    .setMessage("Inventory updated")
			                    .setStatus(HttpStatus.OK.value()));
				
				
			}).orElseThrow(() -> new InventoryNotFoundByIdException("Inventory not found"));
		}
		
	}


