package com.ijse.bookstore.dto;

import lombok.Data;

@Data

public class CartItemCreationDTO {

private long bookId;
private int quantity;
private long userId;
private long cartId;


}
