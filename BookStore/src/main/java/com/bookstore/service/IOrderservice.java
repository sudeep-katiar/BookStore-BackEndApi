package com.bookstore.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.bookstore.entity.Order;

public interface IOrderservice {

	public ResponseEntity<Object> makeOrder(int id,int quantity,int userId);
	public ResponseEntity<Object> makeOrderWithToken(int id,int quantity,String token);
	public ResponseEntity<Object> cancelOrder(int bookId);
	public ResponseEntity<Object> getCartList(int userId);
	public ResponseEntity<Object> getCartListWithToken(String token);
	public ResponseEntity<Object> updateQuantity(Order order) ;
	public ResponseEntity<Object> confirmOrder(List<Order> order);
}
