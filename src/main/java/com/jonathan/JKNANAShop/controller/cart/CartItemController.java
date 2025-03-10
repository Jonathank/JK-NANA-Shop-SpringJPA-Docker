/**
 * 
 */
package com.jonathan.JKNANAShop.controller.cart;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.response.ApiResponse;
import com.jonathan.JKNANAShop.service.cart.ICartItemService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;
    
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
	    @RequestParam Long productId,@RequestParam Integer quantity){
   	try {
   	 cartItemService.addItemToCart(cartId, productId, quantity);
   	    return ResponseEntity.ok(new ApiResponse("Add item Success!", null));
   	} catch (ResourceNotFoundException e) {
   	    return ResponseEntity.status(NOT_FOUND)
   		    .body(new ApiResponse(e.getMessage(), null));
   	} 
   	}
    
    @DeleteMapping("/cart/{cartId}/item{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
	    @PathVariable Long itemId){
   	try {
   	 cartItemService.removeItemFromCart(cartId, itemId);
   	    return ResponseEntity.ok(new ApiResponse("remove item from cart Success!", null));
   	} catch (ResourceNotFoundException e) {
   	    return ResponseEntity.status(NOT_FOUND)
   		    .body(new ApiResponse(e.getMessage(), null));
   	} 
   	}
    
    @PutMapping("/cart/{cartId}/item/{itemId}/quantity/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
	    @PathVariable Long itemId,
	    @RequestParam Integer quantity){
   	try {
   	 cartItemService.updateItemQuantity(cartId, itemId, quantity);
   	    return ResponseEntity.ok(new ApiResponse("update Success!", null));
   	} catch (ResourceNotFoundException e) {
   	    return ResponseEntity.status(NOT_FOUND)
   		    .body(new ApiResponse(e.getMessage(), null));
   	} 
   	}
    
}
