package com.jsp.wms.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderRequest {

	private int orderQuantity;
	private String invoiceLink;
	private int customerId;
}
