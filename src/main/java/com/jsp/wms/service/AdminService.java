package com.jsp.wms.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.wms.requestdto.AdminRequest;
import com.jsp.wms.responsedto.AdminResponse;
import com.jsp.wms.util.ResponseStructure;

import jakarta.validation.Valid;

public interface AdminService {

	public ResponseEntity<ResponseStructure<AdminResponse>> createSuperAdmin(AdminRequest adminRequest);

	public ResponseEntity<ResponseStructure<AdminResponse>> createAdmin(@Valid AdminRequest adminRequest,
			int wareHouseId);

	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@Valid AdminRequest adminRequest);

	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(@Valid AdminRequest adminRequest,
			int adminId);

	public ResponseEntity<ResponseStructure<AdminResponse>> findAdminById(int adminId);

	public ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins();

	
	
}
