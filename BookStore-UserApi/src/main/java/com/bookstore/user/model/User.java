package com.bookstore.user.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;

/*****************************************************************************************************
 * User Entity And Model Class which is mapped with table "user_details"
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 *
 ******************************************************************************************************/

@Data
@Table(name = "user_details")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uId;
	@Column(name = "FIRST_NAME")
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PASSWORD")
	@NotBlank(message = "Password is mandatory")
	private String password;
	@Column(name = "EMAIL", unique = true)
	@NotNull
	private String email;

	@Column(name = "GENDER")
	@NotBlank(message = "Gender is mandatory")
	private String gender;

	@Column(name = "MO_No")
	@NotBlank(message = "contact is mandatory")
	private String phNo;

	@Column(name = "USER_NAME")
	@NotBlank(message = "UserName is mandatory")
	private String userName;
	
	@Column(name = "REGISTRATION_DATE")
	@NotNull
	private String creationTime;

	@Column(name = "LAST_UPDATE")
	private String updateTime;

	@Column(name = "Verified")
	@NotNull
	private boolean activate;
	
	@Column(name="IS_SELLER")
	@NotNull
	private boolean isSeller;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "u_id")
	private List<UserAddress> addresses;

}
