package com.bookstore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Order;
import com.bookstore.util.HibernateUtil;
/***************************************************************************************************
 * OrderDAO Implemantaion Class of iOrderDao its take Support Of HiberNateUtil Class Which Class Which takes All
 * All Hibarnate CURD Operation Methods.
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-14
 * @see {@link HibernateUtil} implementation of all the required DB related functionality
 * 
 ******************************************************************************************************/
@Repository
public class OredrDAOImpl implements IOrderDAO{
	@Autowired
	HibernateUtil<Order> hibernateUtil;
	
	/**
     *To add new order data in order table in DB.  
     * @param Order order
     * @return int
     */
	@Override
	@Transactional
	public int addOrder(Order order) {
		try {
			hibernateUtil.save(order);
			return 1;
		} catch (Exception e) {
			return 0;
		}		
	}
	/**
     *To delete order data from order table in DB.  
     * @param int bookId
     * @return int
     */
	@Override
	@Transactional
	public int deleteOrder(int bookId) {
		
		try {
			String query="DELETE FROM Order WHERE bookId=:bid";
			Query<Order> hQuery=hibernateUtil.createQuery(query);
			hQuery.setParameter("bid", bookId);
			return hQuery.executeUpdate();
		}catch (Exception e) {
			return 0;
		}
	}
	/**
     *To remove All Orders From order table in DB.  
     * @param int userId
     * @return int
     */
	@Override
	@Transactional
	public int removeAllOrder(int userId) {
		
		try {
			String query="DELETE FROM Order WHERE userId=:uid";
			Query<Order> hQuery=hibernateUtil.createQuery(query);
			hQuery.setParameter("uid", userId);
			return hQuery.executeUpdate();
		}catch (Exception e) {
			return 0;
		}
	}

	/**
     *To update books quantity in Order table from DB.  
     * @param Order order
     * @return int
     */
	
	@Transactional
	@Override
	public int updateQuantity(Order order) {
		try {
		String query="UPDATE Order SET quantity=:qty,total=:total WHERE OrderId=:id";
		Query<Order> hQuery=hibernateUtil.createQuery(query);
		hQuery.setParameter("qty", order.getQuantity());
		hQuery.setParameter("id", order.getOrderId());
		hQuery.setParameter("total", order.getQuantity()*order.getPrice());
		return hQuery.executeUpdate();
		}catch (Exception e) {
			System.out.println();
			return 0;
		}
	}

	/**
     * To get All the orders from Order table in Db.  
     * @param int userId
     * @return List<Order>
     */
	@Transactional
	@Override
	public List<Order> getOrderList(int userId) {
		String query="FROM Order";
		Query<Order> hQuery=hibernateUtil.select(query);
//		hQuery.setParameter("id", userId);
		return hQuery.list();
	}
	
	}
