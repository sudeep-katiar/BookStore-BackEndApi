package com.bookstore.user.dto;

import lombok.Data;

/**
 * Model class for Address dto
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-05-05
 */
@Data
public class AddressDto {

    private String street;
    private String town;
    private String district;
    private String state;
    private String country;
    private int pinCode;
    private String customerName;
    private String contact;
    private String type;
}
