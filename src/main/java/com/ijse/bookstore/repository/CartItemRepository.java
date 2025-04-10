package com.ijse.bookstore.repository;

import com.ijse.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE cart_item AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    List<CartItem> findByCartId(Long cartId);

}
