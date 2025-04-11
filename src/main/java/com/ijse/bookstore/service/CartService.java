package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.CartCreationDto;
import com.ijse.bookstore.entity.Cart;
import org.springframework.stereotype.Service;
import com.ijse.bookstore.entity.CartItem;

import java.util.List;

@Service
public interface CartService {

    Cart createCart(CartCreationDto cartCreationDto);
    List<Cart> getAllCart();
    Cart getCartIdByUserId(Long userId);

}
