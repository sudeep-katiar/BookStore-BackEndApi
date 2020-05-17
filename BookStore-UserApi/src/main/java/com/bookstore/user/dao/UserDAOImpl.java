package com.bookstore.user.dao;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.user.model.User;
import com.bookstore.user.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

/***************************************************************************************************
 * UserDao Implemantaion Class of iUserDao its take Supprt Of HiberNateUtil Class Which Class Which takes All
 * All Hibarnate CURD Operation Methods.
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 * @see {@link hibernateUtil} implementation of all the required DB related functionality
 * 
 ******************************************************************************************************/
@Service
@Slf4j
public class UserDAOImpl implements IUserDAO {

	@Autowired
	HibernateUtil<User> hibernateUtil;

	/**
     *To Store The User data 
     * @param User user
     * @return int
     */
	@Transactional
	@Override
	public int register(User user) {
		try {
			hibernateUtil.save(user);
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	/**
     *To check whether user data available in DB or not.  
     * @param User user
     * @return User
     */
	@Transactional
	@Override
	public User isUserExist(User user) {
		String query = "From User where email=:uemail or userName=:uname";
		try {
			Query<User> hQuery = hibernateUtil.createQuery(query);
			hQuery.setParameter("uemail", user.getEmail());
			hQuery.setParameter("uname", user.getUserName());
			return hQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	/**
     *To Update User Detail of varified true.  
     * @param int id
     * @return int
     */
	@Override
	@Transactional
	public int activateUSer(int id) {
		String query = "update User set activate=:activ" + " " + " " + " where uId = :id";

		Query<User> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("activ", true);
		hQuery.setParameter("id", id);
		return hQuery.executeUpdate();
	}
	/**
     *To get User Data by the email.  
     * @param String email
     * @return User
     */
	@Override
	public User getUser(String email) {

		try {
			String query = "FROM User where email=:eml OR userName=:uname";
			Query<User> hquery = hibernateUtil.createQuery(query);
			hquery.setParameter("eml", email);
			hquery.setParameter("uname", email);
			return hquery.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}

	}

	public User getUserById(int id) {
		try {
			String query = "From User where uId=:id";
			Query<User> hquery = hibernateUtil.createQuery(query);
			hquery.setParameter("id", id);
			return hquery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User loginUser(String email, String password) {
		log.info("login User Dao" + email + password);
		String query = "FROM User where email=:eml AND password=:psw";
		try {
			Query<User> hquery = hibernateUtil.createQuery(query);
			hquery.setParameter("eml", email);
			hquery.setParameter("psw", password);
			log.info("User" + hquery.getSingleResult());
			return hquery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
