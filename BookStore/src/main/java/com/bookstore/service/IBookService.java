package com.bookstore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;

public interface IBookService {
		public ResponseEntity<BookResponse> addBook(Book book);

	public ResponseEntity<BookResponse> removeBook(int id);

	public ResponseEntity<BookResponse> getAllBooks();

	public ResponseEntity<BookResponse> updateBookDetails(String bookName, Book updatedBook);

	public ResponseEntity<BookResponse> getSellerBooks();


	public void saveBookImage(MultipartFile file,int bookId);

}
