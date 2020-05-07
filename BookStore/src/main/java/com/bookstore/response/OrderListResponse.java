package com.bookstore.response;

import java.util.List;

import com.bookstore.entity.Order;

import lombok.Data;
@Data
public class OrderListResponse {
	int statusCode;
	String response;
	List<Order> orders;

	public OrderListResponse() {

	}

	public OrderListResponse(int statusCode, String response, List<Order> orders) {
		super();
		this.statusCode = statusCode;
		this.response = response;
		this.orders = orders;
	}
}
