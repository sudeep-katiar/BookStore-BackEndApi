package com.bookstore.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bookstore.entity.Order;

public interface IOrderservice {

	public ResponseEntity<Object> makeOrder(int id,int quantity);
	public ResponseEntity<Object> cancelOrder(int bookId);
	public ResponseEntity<Object> getCartList();
	public ResponseEntity<Object> updateQuantity(Order order) ;
	public ResponseEntity<Object> confirmOrder(List<Order> order);
}
