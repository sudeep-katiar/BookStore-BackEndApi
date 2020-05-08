package com.bookstore.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.dao.BookDaoImpl;
import com.bookstore.dao.IOrderDAO;
import com.bookstore.dto.MailResponse;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.UserData;
import com.bookstore.exception.InvalidTokenOrExpiredException;
import com.bookstore.response.OrderListResponse;
import com.bookstore.response.OrderResponse;
import com.bookstore.util.JwtTokenUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/***************************************************************************************************
 * OrderService Implementation Class By using the object reference of OrderDAO for DataBase Related Support,
 * RestTemplate for comminication between BookStore-Api & BookSrore WebApi, JwtTokenUtil to Generate the Token And parse the token.This Service Class Contains the implementation methods
 * of IBookService Interface methods like Make book Order,delete book Order, Update Book Order,Confirm Order. 
 *
 * @author Rupesh Patil
 * @version 2.0
 * @updated 2020-05-06
 * @created 2020-04-15
 * @see {@link IOrderDAO} implementation of all the required DB related functionality
 * @see {@link restTemplate} will Inject Object for communicate two web-apis.
 * @see {@link JwtTokenUtil} for parse and generate token.
 * @see {@link JavaMailSender} for Send the Mails.
 ******************************************************************************************************/

@Service
@Slf4j
public class OrderServiceImpl implements IOrderservice {

	@Autowired
	IOrderDAO orderDao;
	@Autowired
	BookDaoImpl bookDao;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private JwtTokenUtil generateToken;
	static UserData userData;
	 double finalAmount=0;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	Configuration config;

	/*********************************************************************
     * To make book order by the user.  
     * @param String token, int id, int quantity
     * @return ResponseEntity<Object>
     ********************************************************************/
	@Override
	public ResponseEntity<Object> makeOrder(int id, int quantity) {
			Book book = bookDao.getBookByBookId(id);
			Order order = new Order();
			order.setBookId(id);
				order.setQuantity(quantity);
			order.setBookName(book.getBookName());
			order.setPrice(book.getPrice());
			order.setTotal(order.getPrice()*order.getQuantity());
			order.setBookImage(book.getBookImage());
			if (orderDao.addOrder(order) > 0) {
				book.setQuantity(book.getQuantity()-1);
				bookDao.updateBook(book, book.getBookName());
				System.out.println("Added successfully");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OrderResponse(202, "Order Added to cart"));
			}
		
		return null;
	}

	/*********************************************************************
     * To get List Of Book from the Order list db table.  
     * @param String token
     * @return ResponseEntity<Object>
     ********************************************************************/
	@Override
	public ResponseEntity<Object> getCartList() {
		Optional<List<Order>> orders=null;
		
			orders = Optional.ofNullable(orderDao.getOrderList(1));
			if (orders.isPresent()) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderListResponse(202, "total books in cart" + orders.get().size(), orders.get()));
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderResponse(202, "No any Books Added to cart"));
			}
	}
	/*********************************************************************
     * To update Quantity of books by the user then user can increse or decrease
     * Quantity for purchase books.  
     * @param String token, Order order
     * @return ResponseEntity<Object>
     ********************************************************************/
	@Override
	public ResponseEntity<Object> updateQuantity( Order order) {
		
			if (orderDao.updateQuantity(order) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OrderResponse(202, "Quantity Updatd"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new OrderResponse(500, "Error in Updated Quantity"));
			}
		
	}
	
	/*********************************************************************
     * To cancel the book order by the user it will remove book from cart.
     * 
     * @param String token, int bookId
     * @return ResponseEntity<Object>
     ********************************************************************/
	@Override
	public ResponseEntity<Object> cancelOrder(int bookId) {
		
			if (orderDao.deleteOrder(bookId) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderResponse(202, "Order Deleted SuccessFully"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new OrderResponse(500, "Error in Delete Order"));
			}
		 
	}

	/***************************************************************************
     * To confirm the order by the user after confirmation of order will Send
     * mail to user Attached with FreeMarker template.
	 *
     * @param String token, List<Order> order
     * @return ResponseEntity<Object>
     ********************************************************************/
	@Override
	public ResponseEntity<Object> confirmOrder( List<Order> order) {
	
			MimeMessage message = sender.createMimeMessage();
			Map<String, Object> model = new HashMap<String, Object>();
			order.forEach(s->{
				double temp=0;
				temp =s.getTotal();
				finalAmount +=temp;
			});
			
			model.put("name",userData.getFirstName());
			model.put("total", finalAmount);
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED,
						StandardCharsets.UTF_8.name());
				Template template = config.getTemplate("order-summery.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				helper.setTo(userData.getEmail());
				helper.setText(html, true);
				helper.setSubject("BookStore Order Summery");
				helper.setFrom("pati.rupesh990@gmail.com");
				sender.send(message);
				orderDao.removeAllOrder(userData.getUId());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MailResponse("Mail Sent", "202"));
			} catch (MessagingException | IOException | TemplateException e) {
				System.out.println("Error in message sending");
				e.printStackTrace();
			}
		
		return null;
	}

	/*********************************************************************
     * To verify User whether user is verified or token is valid or not.
	 * will get data from user-Api using Resttemplate.
     * @param String token
     * @return boolean
     ********************************************************************/
	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		userData = restTemplate.getForObject("http://localhost:8092/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		try {
			log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
					+ (userData.getUId() == generateToken.parseToken(token)));
			log.info("erererererererererererererereererereereerererhsghgghsghgsd" + userData.getPhNo());
			return (userData.getUId() == generateToken.parseToken(token));
		} catch (SignatureVerificationException | JWTDecodeException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}
}
