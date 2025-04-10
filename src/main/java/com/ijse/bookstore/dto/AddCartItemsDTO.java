package com.ijse.bookstore.dto;

import lombok.Data;

@Data
public class AddCartItemsDTO {
    private long id;
    private int quantity;
    private double unitPrice;
    private double subTotal;
    private long bookid;
}
