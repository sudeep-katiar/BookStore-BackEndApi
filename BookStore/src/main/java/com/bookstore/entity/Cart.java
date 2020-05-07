package com.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	private int orderId;
	
	@Column(name="created_time")
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private String createdTime;
	@Column(name="last_update")
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private String updaTimestamp;	
	@Column
	private double finalAmount;
}
