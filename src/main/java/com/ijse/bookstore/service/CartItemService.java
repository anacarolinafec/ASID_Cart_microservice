package com.ijse.bookstore.service;

import com.ijse.bookstore.dto.CartItemCreationDTO;
import com.ijse.bookstore.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {
    List<CartItem> getAllCartitem();
    CartItem createCartItem(CartItemCreationDTO createCartItem);
    CartItem getCartItemById(Long id);
    CartItem patchCartQuantity(Long id , CartItem cartItem);
    //CartItem patchCartSubTotal(Long id , CartItem cartItem);
    CartItem deleteCartItyItemById(Long id);
    void clearCart();
    void resetAutoIncrement();

}
