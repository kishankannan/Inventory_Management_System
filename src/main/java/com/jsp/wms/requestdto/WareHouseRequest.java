package com.jsp.wms.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class WareHouseRequest {

	@NotBlank(message = "WareHouse name should not be blank")
	@NotNull(message = "WareHouse name should not be null")
	private String name;
}
