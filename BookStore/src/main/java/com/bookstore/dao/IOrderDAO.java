package com.bookstore.dao;

import java.util.List;

import com.bookstore.entity.Order;
import com.bookstore.util.HibernateUtil;
/***************************************************************************************************
 * OrderDAO contract interface.
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-15
 * @see {@link HibernateUtil} implementation of all the required DB related functionality
 * 
 ******************************************************************************************************/

public interface IOrderDAO {
	public int addOrder(Order order);

	public int deleteOrder(int bookId);

	public List<Order> getOrderList(int userId);
	public int updateQuantity(Order order);
	public int removeAllOrder(int userId);

}
