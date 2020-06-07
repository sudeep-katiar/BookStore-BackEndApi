package com.bookstore.dto;

import java.util.List;

import com.bookstore.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private int invoiceNumber;
	List<Book> orders;
//	private 
}
