/**
 * 
 */
package com.jonathan.JKNANAShop.service.cart;

import java.awt.event.ItemEvent;
import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.stereotype.Service;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Cart;
import com.jonathan.JKNANAShop.model.CartItem;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.repository.CartItemRepository;
import com.jonathan.JKNANAShop.repository.CartRepository;
import com.jonathan.JKNANAShop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepo;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;
    
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
	//1. get cart
	//2. get the product
	//3.check if the product already exists
	//4. if yes, then increase quantity with requested quantity
	//5. if no, initiate anew CartItem entry
	// Retrieve the cart object using the cartId
	Cart cart = cartService.getCart(cartId);

	// Retrieve the product object using the productId
	Product product = productService.getProductById(productId);

	// Check if the product already exists in the cart by filtering cart items
	CartItem cartItem = cart.getCartItems().stream()
	    .filter(Item -> Item.getProduct().getId().equals(productId))
	    .findFirst().orElse(new CartItem()); // If not found, create a new CartItem

	// If the cart item is new (not yet added to the cart)
	if(cartItem.getId() == null) {
	    cartItem.setCart(cart); // Associate the cart item with the cart
	    cartItem.setProduct(product); // Associate the product with the cart item
	    cartItem.setQuantity(quantity); // Set the initial quantity
	    cartItem.setUnitPrice(product.getPrice()); // Set the unit price based on product price
	} else {
	    // If the cart item already exists, update the quantity
	    cartItem.setQuantity(cartItem.getQuantity() + quantity);
	}

	// Recalculate and set the total price for the cart item
	cartItem.setTotalPrice();

	// Add the cart item to the cart (if it's not already added)
	cart.addItem(cartItem);
	cartItemRepo.save(cartItem);
	cartRepository.save(cart);
	 
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
	Cart cart = cartService.getCart(cartId);
	CartItem itemToRemove = getCartItem(cartId,productId);
	cart.removeItem(itemToRemove);
	cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
	Cart cart = cartService.getCart(cartId);
	cart.getCartItems().stream()
	.filter(item -> item.getProduct().getId().equals(productId))
	.findFirst().ifPresent(item ->{
	    item.setQuantity(quantity);
	    item.setUnitPrice(item.getProduct().getPrice());
	    item.setTotalPrice();
	});
	BigDecimal totalAmount = cart.getTotalAmount();
	cart.setTotalAmount(totalAmount);
	cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
	Cart cart = cartService.getCart(cartId);
	return cart.getCartItems().stream()
		.filter(item -> item.getProduct().getId().equals(productId))
		.findFirst().orElseThrow(() -> new ResourceNotFoundException("Itemnot found!"));
    }
}
