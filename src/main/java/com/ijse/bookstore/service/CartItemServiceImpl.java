package com.ijse.bookstore.service;

import com.ijse.bookstore.client.BookServiceHTTPClient;
import com.ijse.bookstore.client.UserServiceHTTPClient;
import com.ijse.bookstore.dto.BookResponseDTO;
import com.ijse.bookstore.dto.CartItemCreationDTO;
import com.ijse.bookstore.dto.UserResponseDTO;
import com.ijse.bookstore.entity.Book;
import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.repository.BookRepository;
import com.ijse.bookstore.repository.CartItemRepository;
import com.ijse.bookstore.repository.CartRepository;
import com.ijse.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService{

    // Injeção de dependências dos repositórios necessários para aceder à base de dados
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookServiceHTTPClient bookServiceHTTPClient;
    @Autowired
    private UserServiceHTTPClient userServiceHTTPClient;

    @Override
    public CartItem createCartItem(CartItemCreationDTO createCartItem){

        BookResponseDTO book = bookServiceHTTPClient.getBookbyId(createCartItem.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("livro nao existe"));

        UserResponseDTO user = userServiceHTTPClient.getUserById(createCartItem.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("livro nao existe"));


        double unitPrice = book.getPrice();
        int quantity = createCartItem.getQuantity();
        double subtotal = quantity * unitPrice;

        // todo verificacao de que a quantidade pedida e inferior ao stock do livro

        CartItem cartItem = new CartItem();
        cartItem.setBookid(book.getId());
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(unitPrice);
        cartItem.setSubTotal(subtotal);
        cartItem.setCart(cart);

        // Or just save the item directly
        return cartItemRepository.save(cartItem);
    }

   public List<CartItem> getAllCartitem(){

        return cartItemRepository.findAll();

    }

    public CartItem getCartItemById(Long id){

        return cartItemRepository.findById(id).orElse(null);
    }


    public CartItem patchCartQuantity(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if (existItem != null) {
            book = existItem.getBookid(); // obtém o livro associado
            int requestedQuantity = cartItem.getQuantity();
            int availableStock = book.getQuantity();

            if (requestedQuantity > availableStock) {
                throw new IllegalArgumentException("Quantidade excede o stock disponível (" + availableStock + ").");
            }

            existItem.setQuantity(requestedQuantity);
            existItem.setSubTotal(book.getPrice() * requestedQuantity); // atualizar subtotal também
            cartItemRepository.save(existItem);

            return existItem;
        } else {
            return null;
        }
    }


    public CartItem patchCartSubTotal(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);
    
        if (existItem != null) {
            
            existItem.setSubTotal(cartItem.getSubTotal());
            cartItemRepository.save(existItem);
    
            return existItem;
        } else { 
            return null;
        }
    }


    public CartItem deleteCartItyItemById(Long id){

        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if(existItem !=null){

            cartItemRepository.delete(existItem);
        }
        return null;
    }


    public void clearCart(){

        cartItemRepository.deleteAll();
        
    }

    public void resetAutoIncrement() {
        cartItemRepository.resetAutoIncrement();
    }

}
