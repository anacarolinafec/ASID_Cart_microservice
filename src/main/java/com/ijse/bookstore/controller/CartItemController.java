package com.ijse.bookstore.controller;

import com.ijse.bookstore.dto.CartItemCreationDTO;
import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;



    @PostMapping("/cartitem")
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItemCreationDTO createCartItem){
    /*Vê que o JSON tem campos como bookId, userId, quantity e cartId;
    Converte esse JSON automaticamente num objeto Java do tipo CartItemCreationDTO*/

        CartItem createdCartItem = cartItemService.createCartItem(createCartItem);

        return new ResponseEntity<>(createdCartItem,HttpStatus.CREATED);
    }

    @GetMapping("/cartitem")
    public ResponseEntity<List<CartItem>> getAllCartItem(){

        List<CartItem> cartItems = cartItemService.getAllCartitem();

        return new ResponseEntity<>(cartItems,HttpStatus.OK);
    }

    @GetMapping("/cartitem/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id){

        CartItem existCartItem  = cartItemService.getCartItemById(id);

        if(existCartItem != null) {
            return new ResponseEntity<>(existCartItem , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/quantity/{id}")
    public ResponseEntity<CartItem> patchCartQuantity(@PathVariable Long id , @RequestBody CartItem cartItem){

        CartItem patchedCartItem = cartItemService.patchCartQuantity(id,cartItem);

        return new ResponseEntity<>(patchedCartItem,HttpStatus.CREATED);
    }

    @PatchMapping("/subtotal/{id}")
    public ResponseEntity<CartItem> patchCartSubTotal(@PathVariable Long id , @RequestBody CartItem cartItem){

        CartItem patchedCartItem = cartItemService.patchCartSubTotal(id,cartItem);

        return new ResponseEntity<>(patchedCartItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/clearcart")
    public ResponseEntity<String> clearCart(){

        cartItemService.clearCart();
        return ResponseEntity.ok("Cart cleared and Id reset.");
    }

    @PostMapping("/reset")
    public void resetAutoIncrement() {
        cartItemService.resetAutoIncrement();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CartItem> deleteCartItyItemById(@PathVariable Long id){

        cartItemService.deleteCartItyItemById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } 
}
