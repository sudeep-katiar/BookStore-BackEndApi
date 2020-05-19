package com.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/*****************************************************************************************************
 * Wishlist Entity And Model Class which is mapped with table "wishlist"
 *  
 * @author Sudeep Kumar Katiar
 * @version 1.0
 * @created 2020-05-11
 *
 ******************************************************************************************************/

@Table(name = "wishlist")
@Data
@Entity
public class Wishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@NotNull
	private int bookId;
	
	@Column
	private int userId;
	
	@Column
	@NotNull
	private String bookName;
	
	@Column
	@NotNull
	private double price;
	
	@Column
	private String bookImage;

}
