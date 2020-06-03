package com.bookstore.entity;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/*****************************************************************************************************
 * Cart Entity And Model Class which is mapped with table "order_details"
 *  
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-15
 *
 ******************************************************************************************************/

@Table(name = "order_details")
@Data
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int invoiceNumber;
	
	@Column(name="created_time")
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private String createdTime;
	
	@Column
	private double finalAmount;
	
	@Column
	private int userId;
	
	@Column
	private String addressType;
	
	@ManyToMany
	@JoinTable(name="books_order",joinColumns = {@JoinColumn(name="invoice_number")},inverseJoinColumns = {@JoinColumn(name="book_id")})
	private Set<Book> books;
	
}
