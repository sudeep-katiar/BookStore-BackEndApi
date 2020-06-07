package com.bookstore.dao;

import com.bookstore.entity.Quantity;

public interface IQuantityDAO {
	
	public boolean addOrderQuantity(Quantity quantity);
	public Quantity getOrdersQuantity(int userId,int bookId);	
}
