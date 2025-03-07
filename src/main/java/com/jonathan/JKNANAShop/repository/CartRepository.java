/**
 * 
 */
package com.jonathan.JKNANAShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonathan.JKNANAShop.model.Cart;

/**
 * @author JONATHAN
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

}
