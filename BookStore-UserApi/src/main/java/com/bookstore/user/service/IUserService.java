package com.bookstore.user.service;

import com.bookstore.user.dto.AddressDto;
import com.bookstore.user.model.UserAddress;
import org.springframework.http.ResponseEntity;

import com.bookstore.user.model.User;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;

import java.util.List;

/*****************************************************************************************************
 * Its Interface for A User Api Contract Methods
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 * @updated 2020-05-05
 * @modified -> api for address add/remove/get added
 * @author Durgasankar Mishra
 * 
 ******************************************************************************************************/
public interface IUserService {
	public ResponseEntity<UserResponse> register(User user);
	public ResponseEntity<UserResponse> activateUser(String token);
	public ResponseEntity<Object> loginUser(String email, String password);
	public ResponseEntity<UserData> getUserByID(String token);
	public ResponseEntity<Object> loginAdmin(String email, String password);

	/**
	 * takes user's {@link AddressDto} and token as input parameter and after successful
	 * authentication add the address of the user to database.
	 *
	 * @param addressDto as {@link AddressDto}
	 * @param token      as String Jwt token
	 * @return Boolean
	 */
	boolean isUserAddressAdded( AddressDto addressDto, String token );

	/**
	 * takes addressId and token as input parameter and after successful authentication remove
	 * the particular address of the user from database.
	 *
	 * @param addressId as Integer
	 * @param token     as String Jwt token
	 * @return Boolean
	 */
	boolean isUserAddressRemoved( long addressId, String token );

	/**
	 * takes user token and and after successful authentication returns list of all user's address
	 *
	 * @param token as String Jwt token
	 * @return List<UserAddress>
	 */
	List<UserAddress> getAllAddressOfUser( String token );

}
