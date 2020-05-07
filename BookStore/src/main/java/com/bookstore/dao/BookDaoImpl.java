package com.bookstore.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.StaleObjectStateException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Book;
import com.bookstore.exception.InternalServerError;
import com.bookstore.util.HibernateUtil;
/***************************************************************************************************
 * OrderDAO Implemantaion Class of IBookDao its take Support Of HiberNateUtil Class Which Class Which takes All
 * All Hibarnate CURD Operation Methods.
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-11
 * @see {@link HibernateUtil} implementation of all the required DB related functionality
 * 
 ******************************************************************************************************/

@Repository
public class BookDaoImpl implements IBookDAO {

	@Autowired
	HibernateUtil<Book> hibernateUtil;
	/**
     *To add new Book data in books_details table in DB 
     * @param Book book
     * @return void
     */
	@Override
	@Transactional
	public void addBook(Book book) {
		try {
			hibernateUtil.save(book);
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}
	/**
     *To getSingle book details by bookcode from books_details table 
     * @param int bookCode
     * @return Book
     */
	@Override
	@Transactional
	public Book getCurrentBook(int bookCode) {
		try {
			return hibernateUtil.getBook(bookCode);
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}
	/**
     *To getSingle book details by BookName from books_details table 
     * @param String bookName
     * @return Book
     */
	@Override
	@Transactional
	public Book getBookByName(String bookName) {
		try {
			String query = "From Book where bookName=:name";

			Query<Book> book = hibernateUtil.select(query);
			book.setParameter("name", bookName);
			return book.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	/**
     *To delete Single book details by book id from books_details table 
     * @param int id
     * @return int
     */
	@Override
	@Transactional
	public int deleteBook(int id) {
		String query = "DELETE FROM Book where bookId=:id";
		Query<Book> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("id", id);
		return hQuery.executeUpdate();
	}
	/**
     *To update  books_details table 
     * @param Book book, String bookName
     * @return void
     */
	@Override
	@Transactional
	public void updateBook(Book book, String bookName) {
		try {
			hibernateUtil.update(book);;
		} catch (StaleObjectStateException e) {
			throw new InternalServerError();
		}
	}
	/**
     *To get All books data from  books_details table 
     * @param no params
     * @return List<Book>
     */

	@Override
	@Transactional
	public List<Book> getAllBooks() {
		try {
			String query = "FROM Book";
			Query<Book> books = hibernateUtil.select(query);
			return books.list();
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}
	/**
     * get single book from boos_details table by book id 
     * @param Book book, String bookName
     * @return void
     */

	@Transactional
	public Book getBookByBookId(int id){
		return hibernateUtil.getBook(id);
	}
	/**
     * to upload books images path in book_detail table 
     * @param int bookId,String bookImage
     * @return int
     */
	@Override
	@Transactional
	public int uploadImage(int bookId,String bookImage) {
	
		String query="UPDATE Book Set bookImage=:image where bookId=:id";
		Query<Book> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("image",bookImage);
		hQuery.setParameter("id", bookId);
		return hQuery.executeUpdate();
	}
}
