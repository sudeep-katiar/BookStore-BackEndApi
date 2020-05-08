package com.bookstore.user.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bookstore.user.configuration.PasswordEncoderConfiguration;
import com.bookstore.user.dao.IAddressRepository;
import com.bookstore.user.dao.IUserDAO;
import com.bookstore.user.exception.InvalidTokenOrExpiredException;
import com.bookstore.user.exception.UserAlradyRegisterException;
import com.bookstore.user.model.User;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.service.UserServiceImpl;
import com.bookstore.user.util.EmailGenerator;
import com.bookstore.user.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;
	@Mock
	private IUserDAO userDao;
	@Mock
	private PasswordEncoderConfiguration passwordEncryption;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private EmailGenerator emailGenerate;
	@Mock
	private JwtTokenUtil generateToken;
	@Mock
	private UserData userresponce;
	@Mock
	private IAddressRepository addressRepository;

	@Test
	public void sucessful_registration_user() {
		User newUser = new User();
		newUser.setPassword("password");
		newUser.setEmail("validEmail");
		newUser.setUId(5);
		newUser.setSeller(false);

//		Mockito.doReturn(null).when(userDao).isUserExist(user);
//		Mockito.when(passwordEncryption.passwordEncoder().encode(Mockito.anyString())).thenReturn("jgj");
		Mockito.when(passwordEncryption.passwordEncoder()).thenReturn(bCryptPasswordEncoder);
		Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
//		Mockito.when(userDao.register(user)).thenReturn(1);
//		Mockito.doReturn("token").when(generateToken).generateToken(Mockito.anyLong());
//		Mockito.doNothing().when(emailGenerate).sendEmail(Mockito.anyString(), Mockito.anyString(),
//				Mockito.anyString());
		ResponseEntity<UserResponse> registrationResponse = userServiceImpl.register(newUser);
		Assert.assertEquals(registrationResponse.getStatusCode().value(), HttpStatus.ACCEPTED.value());
	}
	
	@Test(expected = UserAlradyRegisterException.class)
	public void registration_failure_exception_when_user_already_exist() {
		User newUser = new User();
		Mockito.when(userDao.isUserExist(newUser)).thenReturn(new User());
		userServiceImpl.register(newUser);
	}
	
	@Test
	public void activate_user_with_positive_token() {
		String token = "validToken";
		int idValueGraterThanZero = 2;
		int afterActivationReturnValue = 1;
		Mockito.when(generateToken.parseToken(token))
			.thenReturn(idValueGraterThanZero);
		Mockito.when(userDao.activateUSer(idValueGraterThanZero)).thenReturn(afterActivationReturnValue);
		ResponseEntity<UserResponse> varificationResponse = userServiceImpl.activateUser(token);
		log.info("verification response : " + varificationResponse);
		Assert.assertTrue(varificationResponse.getStatusCodeValue() == HttpStatus.ACCEPTED.value());
	}
	
	@Test(expected = InvalidTokenOrExpiredException.class)
	public void activate_user_with_invalid_token_throws_exception() {
		String token = "invalidToken";
		int userIdFetched = 2;
		int returnValueOfUnsucessfulQueryExecution = 0;
		Mockito.when(generateToken.parseToken(token))
			.thenReturn(userIdFetched);
		Mockito.when(userDao.activateUSer(userIdFetched)).thenReturn(returnValueOfUnsucessfulQueryExecution);
		userServiceImpl.activateUser(token);
	}

}
