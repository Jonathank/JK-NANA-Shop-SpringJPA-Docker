/**
 * 
 */
package com.jonathan.JKNANAShop.controller.cart;

import static org.springframework.http.HttpStatus.*;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Cart;
import com.jonathan.JKNANAShop.response.ApiResponse;
import com.jonathan.JKNANAShop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;
    
    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
	try {
	    Cart cart = cartService.getCart(cartId);
	    return ResponseEntity.ok(new ApiResponse("Success!", cart));
	} catch (ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(), null));
	} 
	}

    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse>clearCart(@PathVariable Long cartId){
	try {
	    cartService.clearCart(cartId);
	    return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
	} catch (ResourceNotFoundException e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse("Failed to clear cart "+e.getMessage(), null));
	}
    }
    
    @GetMapping("/cart/{cartId}/total-price")
    public ResponseEntity<ApiResponse>getTotalAmount(@PathVariable Long cartId){
	try {
	    BigDecimal totalprice = cartService.getTotalPrice(cartId);
	    return ResponseEntity.ok(new ApiResponse("Total price", totalprice ));
	} catch (ResourceNotFoundException e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse("Something went wrong! "+e.getMessage(), null));
	}
    }
    
}
