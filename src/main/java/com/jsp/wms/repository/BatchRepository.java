package com.jsp.wms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.wms.entity.Batch;
import com.jsp.wms.entity.Inventory;
import com.jsp.wms.entity.Storage;

public interface BatchRepository extends JpaRepository<Batch, Integer>{

	public List<Batch> findByInventoryAndStorage(Inventory inventory , Storage storage);

	public Optional<Batch> findByInventoryProductId(int productId);
	
}
