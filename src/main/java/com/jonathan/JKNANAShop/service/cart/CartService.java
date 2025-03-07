/**
 * 
 */
package com.jonathan.JKNANAShop.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Cart;
import com.jonathan.JKNANAShop.repository.CartItemRepository;
import com.jonathan.JKNANAShop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    @Override
    public Cart getCart(Long id) {
	Cart cart = cartRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("Cart not found!"));
	BigDecimal totalAmount = cart.getTotalAmount();
	cart.setTotalAmount(totalAmount);
	return cartRepo.save(cart);
    }

    @Override
    public void clearCart(Long id) {
	Cart cart = getCart(id);
	cartItemRepo.deleteAllByCartId(id);
	cart.getCartItems().clear();
	cartRepo.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
	Cart cart = getCart(id);
	return cart.getTotalAmount();
    }

}
