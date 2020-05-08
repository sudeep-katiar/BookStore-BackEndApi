package com.bookstore.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bookstore.user.dto.AddressDto;
import com.bookstore.user.model.User;
import com.bookstore.user.model.UserAddress;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * User controller test cases
 * 
 * @author Durgasankar Mishra
 * @created 2020-05-08
 * @version 1.0
 * @see {@link UserController} user controller
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class UserControllerTest {

	@InjectMocks
	private UserController userController;
	@Mock
	private IUserService userService;
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	private static final String ADD_ADDRESS_URI = "/users/address/add";
	private static final String REGISTER_SELLER = "/users/seller-register";
	private static final String GET_ADDRESSES_URI = "/users/address/get";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void add_address_test_with_positive_input_value() throws Exception {
		objectMapper = new ObjectMapper();
		String addressDto = objectMapper.writeValueAsString(new AddressDto());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(ADD_ADDRESS_URI).content(addressDto)
				.header("token", "validToken").contentType(MediaType.APPLICATION_JSON);

		Mockito.when(userService.isUserAddressAdded(Mockito.any(), Mockito.anyString())).thenReturn(true);

		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();

		log.info("fetch result : " + fetchedResponse.getContentAsString());
		Assert.assertEquals("Checking sucessful addition of address", fetchedResponse.getStatus(),
				HttpStatus.OK.value());
	}

	@Test
	public void add_address_test_with_failue() throws Exception {
		objectMapper = new ObjectMapper();
		String addressDto = objectMapper.writeValueAsString(new AddressDto());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(ADD_ADDRESS_URI).content(addressDto)
				.header("token", "validToken").contentType(MediaType.APPLICATION_JSON);

		Mockito.when(userService.isUserAddressAdded(Mockito.any(), Mockito.anyString())).thenReturn(false);

		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();

		log.info("fetch result : " + fetchedResponse.getContentAsString());
		Assert.assertEquals("Checking failure scenario of add address", fetchedResponse.getStatus(),
				HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void get_addresses_test_for_valid_addresses() throws Exception {
		UserAddress address1 = new UserAddress();
		address1.setCountry("India");
		address1.setState("Odisha");
		address1.setPinCode(756045);
		UserAddress address2 = new UserAddress();
		address2.setCountry("USA");
		address2.setState("New York");
		address2.setPinCode(100458);
		List<UserAddress> addresses = new ArrayList<>();
		addresses.add(address1);
		addresses.add(address2);
		log.info("created addresses : " + addresses.toString());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(GET_ADDRESSES_URI)
				.header("token", "validToken").contentType(MediaType.APPLICATION_JSON);

		Mockito.when(userService.getAllAddressOfUser(Mockito.anyString())).thenReturn(addresses);

		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();

		log.info("fetch result : " + fetchedResponse.getContentAsString());
		Assert.assertEquals("all Addresses : ", fetchedResponse.getStatus(), HttpStatus.OK.value());

		
	}

	@Test
	public void register_seller_user_with_valid_seller_informations() throws Exception {
		User user = new User();
		user.setEmail("hgshjsjcs");
		UserResponse userResponse = new UserResponse();

		ResponseEntity<UserResponse> responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new UserResponse(208, "test" + user.getEmail() + "<=== please verify your email first"));
		objectMapper = new ObjectMapper();
		String newUserDto = objectMapper.writeValueAsString(user);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(REGISTER_SELLER)
				.content(newUserDto).contentType(MediaType.APPLICATION_JSON);
		Mockito.when(userService.register(Mockito.any())).thenReturn(responseEntity);

		MockHttpServletResponse fetchedResponse = mockMvc.perform(requestBuilder).andReturn().getResponse();

		log.info("fetch result : " + fetchedResponse.getStatus());
		Assert.assertEquals("Checking failure scenario of add address", fetchedResponse.getStatus(),
				202);
	}
}
