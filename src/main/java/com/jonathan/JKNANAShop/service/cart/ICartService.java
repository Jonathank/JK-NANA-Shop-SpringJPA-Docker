/**
 * 
 */
package com.jonathan.JKNANAShop.service.cart;

import java.math.BigDecimal;

import com.jonathan.JKNANAShop.model.Cart;

/**
 * @author JONATHAN
 */
public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
