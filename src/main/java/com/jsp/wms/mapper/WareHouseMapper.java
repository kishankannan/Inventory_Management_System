package com.jsp.wms.mapper;

import org.springframework.stereotype.Component;

import com.jsp.wms.entity.Address;
import com.jsp.wms.entity.WareHouse;
import com.jsp.wms.requestdto.WareHouseRequest;
import com.jsp.wms.responsedto.WareHouseResponse;

@Component
public class WareHouseMapper {

	public WareHouse mapToWareHouse(WareHouseRequest wareHouseRequest, WareHouse wareHouse)
	{
		wareHouse.setName(wareHouseRequest.getName());
		return wareHouse;
	}

	public WareHouseResponse mapToWareHouseResponse(WareHouse wareHouse)
	{
		return WareHouseResponse.builder()
				.wareHouseId(wareHouse.getWareHouseId())
				.name(wareHouse.getName())
				.build();
	}

	public WareHouseResponse mapToWareHouseAddress(Address address , WareHouse warehouse) {

		return WareHouseResponse.builder()
				.addressId(address.getAddressId())
				.addressLine(address.getAddressLine())
				.city(address.getCity())
				.state(address.getState())
				.country(address.getCountry())
				.pincode(address.getPincode())

				.wareHouseId(warehouse.getWareHouseId())
				.name(warehouse.getName())
				.build();
	}
}
