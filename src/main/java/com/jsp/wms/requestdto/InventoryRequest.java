package com.jsp.wms.requestdto;

import java.time.LocalDate;
import java.util.List;

import com.jsp.wms.enums.MaterialType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

	private String productTitle;
	private double lengthInMeters;
	private double breadthInMeters;
	private double heightInMeters;
	private double weightInKg;
	
	List<MaterialType> materialTypes;
	private int sellerId;
	
}
