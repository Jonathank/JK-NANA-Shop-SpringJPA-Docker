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
import org.springframework.web.bind.annotation.RestController;

import com.jonathan.JKNANAShop.exception.AlreadyExistException;
import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Category;
import com.jonathan.JKNANAShop.response.ApiResponse;
import com.jonathan.JKNANAShop.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    
    private final ICategoryService catService;
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
	try {
	    List<Category>categories = catService.getAllCategorys();
	    return ResponseEntity.ok(new ApiResponse("Found!",categories));
	} catch (Exception e) {
	   return ResponseEntity.status(INTERNAL_SERVER_ERROR)
		   .body(new ApiResponse("Error : ",INTERNAL_SERVER_ERROR));
	}
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addCategory(@RequestBody Category catName){
	try {
	    Category category = catService.addCategory(catName);
	    return ResponseEntity.ok(new ApiResponse("Success!",category));
	} catch (AlreadyExistException e) {
	   return ResponseEntity.status(CONFLICT)
		   .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @GetMapping("/category/{categoryId}/getById")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long categoryId){
	try {
	    Category category = catService.getCategoryById(categoryId);
	    return ResponseEntity.ok(new ApiResponse("Found!",category));
	} catch ( ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @GetMapping("/category/{categoryName}/getByName")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String categoryName){
	try {
	    Category category = catService.getCategoryByName(categoryName);
	    return ResponseEntity.ok(new ApiResponse("Found!",category));
	} catch ( ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    
    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse>deletCategory(@PathVariable Long categoryId){
	try {
	    catService.deleteCategory(categoryId);
	    return ResponseEntity.ok(new ApiResponse("Found!",null));
	} catch ( ResourceNotFoundException e) {
	    return ResponseEntity.status(NOT_FOUND)
		    .body(new ApiResponse(e.getMessage(),null));
	}
    }
    
    @PutMapping("/category/{catId}/update")
  public ResponseEntity<ApiResponse>updateCategory(@RequestBody Category category, @PathVariable Long catId){
      try {
	Category updateCategory = catService.updateCategory(category, catId);
	  return ResponseEntity.ok(new ApiResponse("Update success!",updateCategory));
    } catch ( ResourceNotFoundException e) {
	return ResponseEntity.status(NOT_FOUND)
		.body(new ApiResponse(e.getMessage(),null));
    }
  }

}
