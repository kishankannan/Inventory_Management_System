package com.jsp.wms.serviceimpl;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.requestdto.WareHouseRequest;
import com.jsp.wms.service.WareHouseService;
import com.jsp.wms.util.ResponseStructure;

public class WareHouseServiceImpl implements WareHouseService{

	@Override
	public ResponseEntity<ResponseStructure<WareHouse>> createWareHouse(WareHouseRequest wareHouseRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
