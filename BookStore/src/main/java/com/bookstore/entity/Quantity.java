package com.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Table(name="ordered_books_quantity")
@Data
@Entity
@ToString
public class Quantity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private int invoiceNumber;

	@Column
	private int quantity;
	@Column(name="created_time")
	@JsonFormat(pattern="yyyy-MM-dd",timezone ="Asia/Kolkata")
	private String createdTime;
	@Column
	private int bookId;
	@Column
	private int userId;
	
}
