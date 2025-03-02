/**
 * 
 */
package com.jonathan.JKNANAShop.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.request.AddProductRequest;
import com.jonathan.JKNANAShop.request.ProductUpdateRequest;
import com.jonathan.JKNANAShop.response.ApiResponse;
import com.jonathan.JKNANAShop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN 
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    
    private final IProductService proService;
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllProducts(){
	try {
	    List<Product> products = proService.getAllProducts();
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	} catch (Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse("Error : ", INTERNAL_SERVER_ERROR));
	}
    }

    @GetMapping("/product/{productId}/getById")
    public ResponseEntity<ApiResponse>getProductById(@PathVariable Long productId){
	try {
	    Product product = proService.getProductById(productId);
	    return ResponseEntity.ok(new ApiResponse("success!",product));
	} catch (ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @PostMapping("/product/Addnew")
    public ResponseEntity<ApiResponse>addProduct(@RequestBody AddProductRequest product){
	try {
	    Product theProduct = proService.addProduct(product);
	    return ResponseEntity.ok(new ApiResponse("Add product success!",theProduct));
	} catch (Exception e) {
	   return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		   .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse>updateProduct(@RequestBody ProductUpdateRequest product,@PathVariable Long productId){
	try {
	    Product theProduct = proService.updateProduct(product, productId);
	    return ResponseEntity.ok(new ApiResponse("Product Update success!", theProduct));
	} catch (ResourceNotFoundException e) {
	  return ResponseEntity.status(NOT_FOUND)
		  .body(new ApiResponse(e.getMessage(), null));
	}
	
    }
    
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse>deleteProduct(@PathVariable Long productId){
	try {
	    proService.deleteProduct(productId);
	    return ResponseEntity.ok(new ApiResponse("Delete Product success!",null));
	} catch (ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse>getProductByBrandName(@RequestParam String brandName,@RequestParam String productName){
	try {
	    List<Product>products = proService.getProductByBrandAndName(brandName, productName);
	    if(products.isEmpty()) {
		return ResponseEntity.ok(new ApiResponse("No Product found ",null));
	    }
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	}catch(Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse>getProductByCategoryAndBrand(@RequestParam String categoryName,@RequestParam String brandName){
	try {
	    List<Product>products = proService.getProductsByCategoryAndBrand(categoryName, brandName);
	    if(products.isEmpty()) {
		return ResponseEntity.ok(new ApiResponse("No Product found ",null));
	    }
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	}catch(Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    @GetMapping("/products/{productName}/by/name")
    public ResponseEntity<ApiResponse>getProductByName(@PathVariable String productName){
	try {
	    List<Product>products = proService.getProductByName(productName);
	    if(products.isEmpty()) {
		return ResponseEntity.ok(new ApiResponse("No Product found ",null));
	    }
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	}catch(Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    @GetMapping("/products/by/brand")
    public ResponseEntity<ApiResponse>getProductByBrand(@RequestParam String brandName){
	try {
	    List<Product>products = proService.getProductsByBrand(brandName);
	    if(products.isEmpty()) {
		return ResponseEntity.ok(new ApiResponse("No Product found ",null));
	    }
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	}catch(Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    @GetMapping("/products/by/{cateoryName}/category")
    public ResponseEntity<ApiResponse>getProductByCategory(@PathVariable String cateoryName){
	try {
	    List<Product>products = proService.getProductsByCategory(cateoryName);
	    if(products.isEmpty()) {
		return ResponseEntity.ok(new ApiResponse("No Product found ",null));
	    }
	    return ResponseEntity.ok(new ApiResponse("success!",products));
	}catch(Exception e) {
	    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		    .body(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    @GetMapping("/products/count/by-brand/and-name")
    public ResponseEntity<ApiResponse>countProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
	try {
	    var productCount = proService.countProductsByBrandAndName(brandName, productName);
	    return ResponseEntity.ok(new ApiResponse("Product count! ",productCount));
	    
	}catch(Exception e) {
	    return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
	} 
    }
    
    
    
}
