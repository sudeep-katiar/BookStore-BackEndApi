package com.bookstore.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookstore.dto.PlacedOrderDetail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class OrderResponse {
	int statusCode;
	String response;
	List<PlacedOrderDetail> orders;

	public OrderResponse(int statusCode, String response) {
		this.statusCode = statusCode;
		this.response = response;
	}
	
	public OrderResponse(List<PlacedOrderDetail> orders) {
		this.orders = orders;
	}
	
}
