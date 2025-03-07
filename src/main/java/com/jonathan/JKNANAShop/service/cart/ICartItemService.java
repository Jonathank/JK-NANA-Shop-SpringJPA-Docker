/**
 * 
 */
package com.jonathan.JKNANAShop.service.cart;

import com.jonathan.JKNANAShop.model.CartItem;

/**
 * @author JONATHAN
 */
public interface ICartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    /**
     * @param cartId
     * @param productId
     * @return
     */
    CartItem getCartItem(Long cartId, Long productId);
    
}
