package com.ijse.bookstore.service;

import com.ijse.bookstore.client.UserServiceHTTPClient;
import com.ijse.bookstore.dto.CartCreationDto;
import com.ijse.bookstore.dto.UserResponseDTO;
import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.repository.CartItemRepository;
import com.ijse.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService{
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserServiceHTTPClient userServiceHTTPClient;


    @Override
    public Cart createCart(CartCreationDto cartCreationDto) {

        UserResponseDTO user = userServiceHTTPClient.getUserById(cartCreationDto.getId_user())
                .orElseThrow(() -> new IllegalArgumentException("User nao existe"));

        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = new Cart();
        cart.setCreatedDate(LocalDate.now());
        cart.setUserId(user.getId());
        cart.setCartItems(cartItems);

        double total = 0.0;
        cart.setTotal(total);

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCart(){

        return cartRepository.findAll();
    }

    @Override
    public Cart getCartIdByUserId(Long userId){

        return cartRepository.getCartIdByUserId(userId);
    }





}
