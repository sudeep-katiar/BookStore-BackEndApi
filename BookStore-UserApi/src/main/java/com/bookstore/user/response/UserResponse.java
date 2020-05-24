package com.bookstore.user.response;

import java.util.List;

import com.bookstore.user.model.UserAddress;
import org.springframework.stereotype.Component;

import com.bookstore.user.model.User;

import lombok.Data;

@Component
@Data
public class UserResponse {

	User user;
	int status;
	String response;

	List<User> usersList;

	List<UserAddress> addresses;

	
	public UserResponse() {
		}

	public UserResponse(int status, String response) {
		this.status = status;
		this.response = response;
	}
	public UserResponse(User user, int status, String response) {
		this(status, response);
		this.user = user;
	}

	public UserResponse(int status, String response, List<User> usersList) {
		this(status, response);
		this.usersList = usersList;
	}

	/**
	 * Constructor
	 *
	 * @param response  as String
	 * @param addresses as List<UserAddress>
	 */
	public UserResponse( String response, List<UserAddress> addresses ) {
		this.response = response;
		this.addresses = addresses;
	}


}
