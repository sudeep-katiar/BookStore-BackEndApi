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
 * Order Entity And Model Class which is mapped with table "orders"
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-14
 *
 ******************************************************************************************************/

@Table(name="orders")
@Data
@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int OrderId;
	
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
	private int quantity;
	
	@Column
	@NotNull
	private double price;
	
	@Column
	@NotNull
	private double total;
		
	@Column
	private String bookImage;
}
