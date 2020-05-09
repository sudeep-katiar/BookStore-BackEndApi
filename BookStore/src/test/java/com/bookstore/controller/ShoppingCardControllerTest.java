package com.bookstore.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bookstore.controller.ShoppingCardController;
import com.bookstore.entity.Order;
import com.bookstore.service.IOrderservice;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Shopping card controller test cases
 *
 * @author Lakshmi Prasad A
 * @created 2020-05-08
 * @version 1.0
 * @see {@link ShoppingCardController}Shopping card controller
 */
@RunWith(MockitoJUnitRunner.class)
public class ShoppingCardControllerTest {
	@InjectMocks
	private ShoppingCardController shoppingCardController;
	@Mock
	private IOrderservice orderService;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	Order order = new Order();
	private static final String make_order_URI = "/orders/make-order";
	private static final String remove_order_URI = "/orders/remove-order";
	private static final String update_quantity_URI = "/orders/update-quantity";
	private static final String confirm_order_URI = "/orders/confirm-order";
	private static final String get_cart_list_URI = "/orders/cart-list";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(shoppingCardController).build();
	}

	@Test
	public void makeorder() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(make_order_URI)
				.requestAttr("bookId", 1).requestAttr("qty", 1).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals("Order Added to cart", fetchedResponse.getStatus(), HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void removeOrder() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(remove_order_URI)
				.requestAttr("bookId", 1).contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals("Order Deleted SuccessFully", fetchedResponse.getStatus(), HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void updateQuantity() throws Exception {
		objectMapper = new ObjectMapper();
		String order = objectMapper.writeValueAsString(new Order());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(update_quantity_URI).content(order)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals("Quantity Updatd", fetchedResponse.getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void confirmOrder() throws Exception {
		objectMapper = new ObjectMapper();
		String order = objectMapper.writeValueAsString(new Order());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(confirm_order_URI).content(order)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals("order confirmed", fetchedResponse.getStatus(), HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void getCartList() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(get_cart_list_URI)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();
		Assert.assertEquals("get cartList", fetchedResponse.getStatus(), HttpStatus.OK.value());
	}
}
