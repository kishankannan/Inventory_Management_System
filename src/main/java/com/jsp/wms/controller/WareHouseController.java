package com.jsp.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.requestdto.AdminRequest;
import com.jsp.wms.requestdto.WareHouseRequest;
import com.jsp.wms.responsedto.AdminResponse;
import com.jsp.wms.responsedto.WareHouseResponse;
import com.jsp.wms.service.WareHouseService;
import com.jsp.wms.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class WareHouseController {

	@Autowired
	private WareHouseService wareHouseService;

	@PreAuthorize("hasAuthority('CREATE_WAREHOUSE')")
	@PostMapping("/warehouses")
	public ResponseEntity<ResponseStructure<WareHouseResponse>> createWareHouse(@RequestBody WareHouseRequest wareHouseRequest){
		return wareHouseService.createWareHouse(wareHouseRequest);
	}
}
