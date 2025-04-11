package com.ijse.bookstore.service;

import com.ijse.bookstore.client.BookServiceHTTPClient;
import com.ijse.bookstore.client.UserServiceHTTPClient;
import com.ijse.bookstore.dto.BookResponseDTO;
import com.ijse.bookstore.dto.CartItemCreationDTO;
import com.ijse.bookstore.dto.UserResponseDTO;
import com.ijse.bookstore.entity.Cart;
import com.ijse.bookstore.entity.CartItem;
import com.ijse.bookstore.repository.CartItemRepository;
import com.ijse.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        double bookPrice = book.getPrice();
        int requestedQuantity = verifyStock(createCartItem, book);
        double subtotal = bookPrice * requestedQuantity;

        Cart cart = Optional.ofNullable(cartRepository.getCartIdByUserId(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("cart nao existe"));
        double cart_total_before = cart.getTotal();
        double cart_total_after = cart_total_before + subtotal;
        cart.setTotal(cart_total_after);
        cart = cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setBookid(book.getId());
        cartItem.setQuantity(requestedQuantity);
        cartItem.setUnitPrice(bookPrice);
        cartItem.setSubTotal(subtotal);
        cartItem.setCart(cart);

        return cartItemRepository.save(cartItem);

    }

   public List<CartItem> getAllCartitem(){
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(Long id){
        return cartItemRepository.findById(id).orElse(null);
    }

    public int verifyStock(CartItemCreationDTO cartitem, BookResponseDTO book) {

        int requestedQuantity = cartitem.getQuantity();

        int availableStock = book.getQuantity();

        if (requestedQuantity > availableStock) {
            throw new IllegalArgumentException("Quantidade pedida excede o stock disponível (" + availableStock + ").");
        }
        return requestedQuantity;
    }

    public CartItem patchCartQuantity(Long id, CartItem cartItem){
        CartItem existItem = cartItemRepository.findById(id).orElse(null);

        if (existItem != null) {
            long bookId = existItem.getBookid(); // obtém o livro associado
            int requestedQuantity = cartItem.getQuantity();

            BookResponseDTO book = bookServiceHTTPClient.getBookbyId(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

            int availableStock = book.getQuantity(); //quantidade em stock do livro

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
