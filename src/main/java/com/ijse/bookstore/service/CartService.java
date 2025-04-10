package com.ijse.bookstore.service;

import com.ijse.bookstore.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    Cart createCart(BookCreationDto cartCreationDto);
    List<Cart> getAllCart();
    Cart getCartIdByUserId(Long userId);
    double calculateCartTotal(long userId);
}
