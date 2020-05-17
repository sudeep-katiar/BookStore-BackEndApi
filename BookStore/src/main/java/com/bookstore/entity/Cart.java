package com.bookstore.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_number")
	private List<Order> orders;
	
}
