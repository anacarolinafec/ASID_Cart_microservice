package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.CartCreationDto;
import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    
    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart(@RequestBody CartCreationDto cartCreationDto){

        Cart updatedCart = cartService.createCart(cartCreationDto);

        
        return new ResponseEntity<>(updatedCart,HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getAllCart(){

        List<Cart> existcart = cartService.getAllCart();

        return new ResponseEntity<>(existcart,HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getCartIdByUserId(@PathVariable Long userId) {

        Cart cartId = cartService.getCartIdByUserId(userId);
        if (cartId != null) {
            return new ResponseEntity<>(cartId, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}


