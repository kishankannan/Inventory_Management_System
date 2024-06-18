package com.jsp.wms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jsp.wms.entity.Admin;
import com.jsp.wms.enums.AdminType;
import com.jsp.wms.exception.AdminNotFoundByEmailException;
import com.jsp.wms.exception.AdminNotFoundByIdException;
import com.jsp.wms.exception.IllegalOperationException;
import com.jsp.wms.exception.WarehouseNotFoundByIdException;
import com.jsp.wms.mapper.AdminMapper;
import com.jsp.wms.repository.AdminRepository;
import com.jsp.wms.repository.WareHouseRepository;
import com.jsp.wms.requestdto.AdminRequest;
import com.jsp.wms.responsedto.AdminResponse;
import com.jsp.wms.service.AdminService;
import com.jsp.wms.util.ResponseStructure;

import jakarta.validation.Valid;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private AdminMapper adminMapper; 
	@Autowired
	private WareHouseRepository wareHouseRepository;

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> createSuperAdmin(AdminRequest adminRequest) {
		if(adminRepository.existsByAdminType(AdminType.SUPER_ADMIN))
			throw new IllegalOperationException("Illegal operation");

		Admin admin = adminRepository.save(adminMapper.mapToAdmin(adminRequest, new Admin()));
		admin.setAdminType(AdminType.SUPER_ADMIN);
		admin = adminRepository.save(admin);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseStructure<AdminResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("Admin created")
						.setData(adminMapper.mapToAdminResponse(admin)));
	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> createAdmin(AdminRequest adminRequest,int wareHouseId) {
		return wareHouseRepository.findById(wareHouseId).map(warehouse -> {

			Admin admin = adminMapper.mapToAdmin(adminRequest, new Admin());
			admin.setAdminType(AdminType.ADMIN);
			admin=adminRepository.save(admin);

			warehouse.setAdmin(admin);
			wareHouseRepository.save(warehouse);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseStructure<AdminResponse>()
							.setStatus(HttpStatus.CREATED.value())
							.setMessage("Admin created")
							.setData(adminMapper.mapToAdminResponse(admin)));
		}).orElseThrow(() -> new WarehouseNotFoundByIdException("Warehouse with such an id does not exists"));

	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@Valid AdminRequest adminRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();

		return adminRepository.findByEmail(name).map(exAdmin ->{
			
			exAdmin = adminMapper.mapToAdmin(adminRequest, exAdmin);

			Admin admin = adminRepository.save(exAdmin);

            return ResponseEntity.status(HttpStatus.OK)
            		.body(new ResponseStructure<AdminResponse>()
            				.setStatus(HttpStatus.OK.value())
            				.setMessage("Admin Updated")
            				.setData(adminMapper.mapToAdminResponse(admin)));
            		}).orElseThrow(()-> new AdminNotFoundByEmailException("Admin not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(@Valid AdminRequest adminRequest,
			int adminId) {
		
		return adminRepository.findById(adminId).map(exAdmin ->{
			
			exAdmin = adminMapper.mapToAdmin(adminRequest, exAdmin);
			
			Admin admin = adminRepository.save(exAdmin);
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<AdminResponse>()
							.setStatus(HttpStatus.OK.value())
							.setMessage("Admin updated")
							.setData(adminMapper.mapToAdminResponse(admin)));
		}).orElseThrow(()-> new AdminNotFoundByIdException("Admin not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdminById(int adminId) {
		
		return adminRepository.findById(adminId).map(admin -> {
			
			return ResponseEntity.status(HttpStatus.FOUND)
					.body(new ResponseStructure<AdminResponse>()
							.setStatus(HttpStatus.FOUND.value())
							.setMessage("Admin Found")
							.setData(adminMapper.mapToAdminResponse(admin)));
		}).orElseThrow(()-> new AdminNotFoundByIdException("Admin not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins() {
		List<AdminResponse> admins = adminRepository.findAllByAdminType(AdminType.ADMIN).stream()
				.map(adminMapper :: mapToAdminResponse).toList();
					
					return ResponseEntity.status(HttpStatus.FOUND)
							.body(new ResponseStructure<List<AdminResponse>>()
									.setStatus(HttpStatus.FOUND.value())
									.setMessage("Admins Found")
									.setData(admins));
				}
	

	
}
