package com.jsp.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.wms.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

}
