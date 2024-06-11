package com.jsp.wms.service;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.requestdto.WareHouseRequest;
import com.jsp.wms.responsedto.WareHouseResponse;
import com.jsp.wms.util.ResponseStructure;


public interface WareHouseService {

	public ResponseEntity<ResponseStructure<WareHouseResponse>> createWareHouse(WareHouseRequest wareHouseRequest);

	public ResponseEntity<ResponseStructure<WareHouseResponse>> updateWareHouse(WareHouseRequest wareHouseRequest,
			int wareHouseId);
	
}
