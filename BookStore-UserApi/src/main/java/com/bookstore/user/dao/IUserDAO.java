package com.bookstore.user.dao;

import com.bookstore.user.model.User;
/***************************************************************************************************
 * Interface Contract Method For UserDao
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 * 
 ******************************************************************************************************/
public interface IUserDAO {

	public int register(User user);
	public User isUserExist(User user);
	public int activateUSer(int id);
	public User getUser(String userName);
	public User loginUser(String email,String password);
	public User getUserById(int id);
	public boolean updatePassword(User user,String password);

}
