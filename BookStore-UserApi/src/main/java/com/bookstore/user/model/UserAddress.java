package com.bookstore.user.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * one to many mapped address which contains several fields and follows
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-05-05
 * @see {@link User}
 */
@Data
@Table(name = "users_addresses")
@Entity
public class UserAddress {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ADDRESS_ID")
	    private long addressId;
	    @Column(length = 25, name = "LOCALITY")
	    private String locality;
	    @Column(length = 25, name = "ADDRESS")
	    private String address;
	    @Column( name = "CITY")
	    private String city;
	    @Column(length = 25, name = "LANDMARK")
	    private String landMark;
	    @Column(length = 25, name = "COUNTRY")
	    @NotBlank(message = "Country name is mandatory")
	    private String country;
	    @Column(name = "PIN_CODE", nullable = false)
	    @NotBlank(message = "Pin code is mandatory")
	    private int pinCode;
	    @Column(length = 25, name = "CUSTOMER_NAME")
	    private String customerName;
	    @Column(length = 25, name = "CONTACT")
	    private String contact;
	    @Column(length = 25, name = "ADDRESS_TYPE")
	    private String type;
}
