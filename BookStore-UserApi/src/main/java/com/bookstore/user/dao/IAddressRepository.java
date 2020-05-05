package com.bookstore.user.dao;

import com.bookstore.user.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * JPA Repository interface for User's address Database Operations
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-05-05
 * @see {@link JpaRepository } for Database Operations
 */
public interface IAddressRepository extends JpaRepository<UserAddress, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from users_addresses where ADDRESS_ID = ? and U_ID = ?", nativeQuery = true)
    void removeAddress( long addressId, long userId );
}
