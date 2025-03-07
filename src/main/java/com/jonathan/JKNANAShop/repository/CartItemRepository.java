/**
 * 
 */
package com.jonathan.JKNANAShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonathan.JKNANAShop.model.CartItem;

/**
 * @author JONATHAN
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    /**
     * @param id
     */
    void deleteAllByCartId(Long id);

}
