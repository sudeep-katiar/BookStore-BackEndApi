package com.bookstore.user.dto;

import lombok.Data;

/**
 * Model class for Address dto
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-05-05
 */
@Data
public class AddressDto {

	private long addressId;
	private String locality;
	private String address;
	private String city;
	private String landMark;
	private String country;

	private int pinCode;
	private String customerName;
	private String contact;
	private String type;
}
