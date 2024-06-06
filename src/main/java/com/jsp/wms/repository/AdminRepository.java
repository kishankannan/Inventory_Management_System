package com.jsp.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.wms.entity.Admin;
import com.jsp.wms.enums.AdminType;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	public boolean existsByAdminType(AdminType adminType);
}
