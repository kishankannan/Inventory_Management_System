package com.jsp.wms.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {

	@NotBlank(message = "Business name should not be blank")
	@NotNull(message = "Business name should not be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Name should only contain alphabetic characters")
	private String businessName;
	
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email should end with @gmail.com")
	private String email;
	
	@Pattern(regexp = "\\d{10}", message = "Invalid phone number")
	private long contactNumber;
}
