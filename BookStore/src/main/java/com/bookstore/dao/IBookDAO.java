package com.bookstore.dao;

import java.util.List;

import com.bookstore.entity.Book;
import com.bookstore.util.HibernateUtil;
/***************************************************************************************************
 * BookDAO contract interface.
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-12
 * @see {@link HibernateUtil} implementation of all the required DB related functionality
 * 
 ******************************************************************************************************/

public interface IBookDAO {
	public void addBook(Book book);
	public void updateBook(Book book,String bookName);
	public Book getCurrentBook(int bookName);
	public Book getBookByName(String bookName);
	public Book getBookByBookId(int id);
	public int deleteBook(int id);
	public List<Book> getAllBooks();
	public int uploadImage(int bookId,String bookImage);
}
