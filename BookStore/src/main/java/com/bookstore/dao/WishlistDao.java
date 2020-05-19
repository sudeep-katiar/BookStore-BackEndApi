package com.bookstore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Wishlist;

@Repository
@Transactional
public interface WishlistDao extends JpaRepository<Wishlist, Long> {
	
	@Query(value = "select * from wishlist where user_id = :userId", nativeQuery = true)
	List<Wishlist> getWishList(int userId);

	@Modifying
	@Query(value = "delete from wishlist where book_id = :id and user_id = :userId", nativeQuery = true)
	void removeBookFromWishlist(long userId, int id);

}
