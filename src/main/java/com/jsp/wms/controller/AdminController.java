package com.jsp.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.wms.requestdto.AdminRequest;
import com.jsp.wms.responsedto.AdminResponse;
import com.jsp.wms.service.AdminService;
import com.jsp.wms.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<AdminResponse>> createSuperAdmin(@RequestBody @Valid AdminRequest adminRequest){
		return adminService.createSuperAdmin(adminRequest);
	}
	
	@PostMapping("/warehouses/{wareHouseId}/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> createAdmin(@RequestBody @Valid AdminRequest adminRequest, @PathVariable int wareHouseId){
		return adminService.createAdmin(adminRequest, wareHouseId);
	}
}
