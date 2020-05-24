package com.bookstore.user.service;

import java.util.List;
import java.util.Optional;

import com.bookstore.user.dao.IAddressRepository;
import com.bookstore.user.dto.AddressDto;
import com.bookstore.user.exception.UserNotFoundException;
import com.bookstore.user.model.UserAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.user.configuration.PasswordEncoderConfiguration;
import com.bookstore.user.dao.IUserDAO;
import com.bookstore.user.exception.AuthenticationFailedException;
import com.bookstore.user.exception.InvalidTokenOrExpiredException;
import com.bookstore.user.exception.UserAlradyRegisterException;
import com.bookstore.user.model.User;
import com.bookstore.user.response.LoginSuccessResponse;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.util.DateValidator;
import com.bookstore.user.util.EmailGenerator;
import com.bookstore.user.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;
/***************************************************************************************************
 * UserService Class By using the object reference of UserDao for DataBase Related Support,passwordEncryption for Password Encryption and decryption,
 * emailGenerate for Sending Mail Using JMS,JwtTokenUtil to Generate the Token And parse the token.This Service Class Contains the implementation methods
 * of IUserService Interface like register,Login and activate user. 
 *
 * @author Rupesh Patil
 * @version 1.0
 * @created 2020-04-12
 * @see {@link IUserDAO} implementation of all the required DB related functionality
 * @see {@link PasswordEncoderConfiguration ,EmailGenerator,JwtTokenUtil,UserData} for encrypt/decrypt Password,Email Generation,Generate/parse Token,UserData
 * for response. 
 * 
 ******************************************************************************************************/

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	private static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "Oops...User not found!";
	private static final int USER_NOT_FOUND_STATUS_CODE = 404;

	@Autowired
	IUserDAO userDao;
	@Autowired
	PasswordEncoderConfiguration passwordEncryption;
	@Autowired
	private EmailGenerator emailGenerate;
	@Autowired
	private JwtTokenUtil generateToken;
	@Autowired
	private UserData userresponce;
	@Autowired
	private IAddressRepository addressRepository;

	/**
     *To register The User  
     * @param user as User
     * @return ResponseEntity<UserResponse>
     */
	@Override
	public ResponseEntity<UserResponse> register(User user) {
		Optional<User> userExist = Optional.ofNullable(userDao.isUserExist(user));
		if (!userExist.isPresent()) {
			log.info("Password Original::" + user.getPassword());
			log.info("Encrypted Password::" + passwordEncryption.passwordEncoder().encode(user.getPassword()));
			user.setPassword(passwordEncryption.passwordEncoder().encode(user.getPassword()));
			log.info(user.getPassword());
			user.setActivate(false);
			user.setCreationTime(DateValidator.getCurrentDate());
			userDao.register(user);

			String link = "http://localhost:4200/activate/" + generateToken.generateToken(user.getUId());
			emailGenerate.sendEmail(user.getEmail(), "Book Store Varification", link);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Verification Link Sent to your email ===>" + user.getEmail()
							+ "<=== please verify your email first"));
		} else {
			throw new UserAlradyRegisterException("User Already Registered", HttpStatus.BAD_REQUEST);
		}
	}

	/**
     *To Activate User/Seller  
     * @param  token as String
     * @return ResponseEntity<UserResponse>
     */
	
	@Override
	public ResponseEntity<UserResponse> activateUser(String token) {
		int id = 0;
		try {
			id = generateToken.parseToken(token);
		} catch (SignatureVerificationException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
		if (userDao.activateUSer(id) > 0) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Your Account Activate Successfully"));
		} else {
			throw new InvalidTokenOrExpiredException("User Not Registered Or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
     *	To Login User	  
     * @param  email as String
     * @param  password as String
     * @return ResponseEntity<UserResponse>
     */

	@Override
	public ResponseEntity<Object> loginUser(String email, String password) {
		Optional<User> user = Optional.ofNullable(userDao.getUser(email));
		if(user.isPresent()) {
		if (user.get().isActivate()) {
			if (user.isPresent() && passwordEncryption.passwordEncoder().matches(password, user.get().getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new LoginSuccessResponse(user.get().getFirstName(), user.get().getLastName(),
								generateToken.generateToken(user.get().getUId()), "Login Success", 202));
			} else {
				throw new AuthenticationFailedException("Invalid UserName Or Password", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new AuthenticationFailedException("Please Verify Email Before Login", HttpStatus.BAD_REQUEST);
		}
		}else {
			throw new AuthenticationFailedException("User Does Not Exist Please Reister", HttpStatus.BAD_REQUEST);
		}
	}

	/**
     * To Login As Admin  
     * @param  email,String password as String
     * @return ResponseEntity<Object>
     */
	
	public ResponseEntity<Object> loginAdmin(String email, String password) {
		Optional<User> user = Optional.ofNullable(userDao.getUser(email));
		if(user.isPresent()) {

		if (user.get().isActivate()) {
			if (user.isPresent() && user.get().isSeller()&&passwordEncryption.passwordEncoder().matches(password, user.get().getPassword())) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new LoginSuccessResponse(user.get().getFirstName(), user.get().getLastName(),
								generateToken.generateToken(user.get().getUId()), "Login Success", 202));
			} else {
				throw new AuthenticationFailedException("Invalid UserName Or Password", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new AuthenticationFailedException("Please Verify Email Before Login", HttpStatus.BAD_REQUEST);
		}
		}else {
			throw new AuthenticationFailedException("User Does Not Exist Please Reister", HttpStatus.BAD_REQUEST);
		}

	}

	/**
     * To Get User From Using Token  
     * @param  token as String
     * @return ResponseEntity<UserData>
     */
	@Override
	public ResponseEntity<UserData> getUserByID(String token) {
		try {
			if (userDao.getUserById(generateToken.parseToken(token)) != null) {
				User user = userDao.getUserById(generateToken.parseToken(token));
				BeanUtils.copyProperties(user, userresponce);
				userresponce.setStatus(302);
				userresponce.setResponse("User Data Found");
				return ResponseEntity.status(HttpStatus.FOUND).body(userresponce);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserData(404, "User Not Found"));
			}
		} catch (SignatureVerificationException | JWTDecodeException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This function checks for the authentication of user if user is authenticated then
	 * creates a new address by taking the input from given address by user and after successfully
	 * addition of address returns boolean value
	 *
	 * @param addressDto as {@link AddressDto}
	 * @param token      as String Jwt token
	 * @return Boolean
	 */
	@Override
	public boolean isUserAddressAdded( AddressDto addressDto, String token ) {
		log.info ("fetched address dto : " + addressDto);
		Optional<User> fetchedUser = getAuthenticatedUser (token);
		if (fetchedUser.isPresent ()) {
			UserAddress newAddress = new UserAddress ();
			BeanUtils.copyProperties (addressDto, newAddress);
			fetchedUser.get ().getAddresses ().add (newAddress);
			addressRepository.saveAndFlush (newAddress);
			return true;
		}
		throw new UserNotFoundException (USER_NOT_FOUND_EXCEPTION_MESSAGE, USER_NOT_FOUND_STATUS_CODE);
	}

	/**
	 * This function takes jwt authentication token as String input parameter and checks
	 * the originality of the user and after successful validation returns Valid User.
	 *
	 * @param token as String jwt.
	 * @return Optional<User>
	 */
	private Optional<User> getAuthenticatedUser( String token ) {
		try {
			return Optional.ofNullable (userDao.getUserById (generateToken.parseToken (token)));
		} catch (SignatureVerificationException | AlgorithmMismatchException e) {
			log.error ("Authentication Failed", e);
			throw new InvalidTokenOrExpiredException ("User Not Registered Or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This function checks for the authentication of user if user is authenticated then
	 * removes the address given by the user and after successfully removal of address
	 * returns boolean value
	 *
	 * @param addressId as Integer
	 * @param token     as String Jwt token
	 * @return Boolean
	 */
	@Override
	public boolean isUserAddressRemoved( long addressId, String token ) {
		Optional<User> fetchedUser = getAuthenticatedUser (token);
		if (fetchedUser.isPresent ()) {
			addressRepository.removeAddress (addressId, fetchedUser.get ().getUId ());
			return true;
		}
		throw new UserNotFoundException (USER_NOT_FOUND_EXCEPTION_MESSAGE, USER_NOT_FOUND_STATUS_CODE);
	}

	/**
	 * This function checks for the authentication of user if user is authenticated then
	 * fetch all the address of the user
	 *
	 * @param token as String Jwt token
	 * @return List<UserAddress>
	 */
	@Override
	public List<UserAddress> getAllAddressOfUser( String token ) {
		Optional<User> fetchedUser = getAuthenticatedUser (token);
		if (fetchedUser.isPresent ())
			return fetchedUser.get ().getAddresses ();
		throw new UserNotFoundException (USER_NOT_FOUND_EXCEPTION_MESSAGE, USER_NOT_FOUND_STATUS_CODE);
	}

}
