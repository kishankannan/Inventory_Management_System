package com.jsp.wms.responsedto;

import java.util.List;

import com.jsp.wms.enums.MaterialType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageResponse {

	private int storageId;
	private String blockName;
	private String section;
	List<MaterialType> materialTypes;
	private double maxAdditionalWeight;
	private double availableArea;
}
