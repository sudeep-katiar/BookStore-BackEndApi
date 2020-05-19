package com.bookstore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bookstore.response.WishlistResponse;

@Component
public interface IWishlistService {
	
	ResponseEntity<WishlistResponse> AddWishlist(int id, int userId);
	
	ResponseEntity<WishlistResponse> AddWishlistWithUser(int id, String token);

	ResponseEntity<WishlistResponse> getWishlist(int userId);
	
	ResponseEntity<WishlistResponse> removeWishlist(int userId, int id);
	
	ResponseEntity<WishlistResponse> getUserWishlist(String token);

	ResponseEntity<WishlistResponse> removeFromWishlist(String token, int id);

}
