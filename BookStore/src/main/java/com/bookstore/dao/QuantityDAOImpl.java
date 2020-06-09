package com.bookstore.dao;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Quantity;
import com.bookstore.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class QuantityDAOImpl implements IQuantityDAO{

	@Autowired
	HibernateUtil<Quantity> hibernateUtil;
	
	@Override
	public boolean addOrderQuantity(Quantity quantity) {
		try {
		hibernateUtil.save(quantity);
		return true;
		}
		catch (Exception e) {
			log.info("",e);
			return false;
		}
	}

	@Override
	public Quantity getOrdersQuantity(int userId,int bookId,String dateTime) {		
		try {
		String query="FROM Quantity where userId=:uid AND bookId=:bid AND createdTime=:cd";
		Query<Quantity> hQuery=hibernateUtil.createQuery(query);
		hQuery.setParameter("uid", userId);
		hQuery.setParameter("bid", bookId);
		hQuery.setParameter("cd", dateTime);
		return hQuery.uniqueResult();
//		return null;
		}catch (Exception e) {
			log.info("",e);
			return null;
		}
	}

}
