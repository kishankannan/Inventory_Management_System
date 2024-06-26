package com.jsp.wms.mapper;

import org.springframework.stereotype.Component;

import com.jsp.wms.entity.PurchaseOrder;
import com.jsp.wms.requestdto.PurchaseOrderRequest;
import com.jsp.wms.responsedto.PurchaseOrderResponse;

@Component
public class PurchaseOrderMapper {

	public PurchaseOrder mapToPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest, PurchaseOrder purchaseOrder)
	{
		purchaseOrder.setOrderQuantity(purchaseOrderRequest.getOrderQuantity());
		purchaseOrder.setInvoiceLink(purchaseOrderRequest.getInvoiceLink());
		purchaseOrder.setCustomerId(purchaseOrderRequest.getCustomerId());
		
		return purchaseOrder;
	}
	
	public PurchaseOrderResponse mapToPurchaseOrderResponse(PurchaseOrder purchaseOrder)
	{
		return PurchaseOrderResponse.builder()
				.orderId(purchaseOrder.getOrderId())
				.orderQuantity(purchaseOrder.getOrderQuantity())
				.invoiceLink(purchaseOrder.getInvoiceLink())
				.customerId(purchaseOrder.getCustomerId())
				.status(purchaseOrder.getStatus())
				.build();
	}
	
}
