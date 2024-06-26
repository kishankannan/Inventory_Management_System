package com.jsp.wms.responsedto;

import java.time.LocalDate;
import java.util.List;

import com.jsp.wms.enums.MaterialType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

	private int productId;
	private String productTitle;
	private double lengthInMeters;
	private double breadthInMeters;
	private double heightInMeters;
	private double weightInKg;
	
	List<MaterialType> materialTypes;
	
	private LocalDate restockedAt;
	private int sellerId;
	
	BatchResponse batch;
	
}
