package com.jsp.wms.responsedto;

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
public class StorageTypeResponse {

	private int storageTypeId;
	private double lengthInMeters;
	private double breadthInMeters;
	private double heightInMeters;
	private double capacityInWeight;
	private int unitsAvailable;
}
