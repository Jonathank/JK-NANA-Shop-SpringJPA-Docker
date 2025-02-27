/**
 * 
 */
package com.jonathan.JKNANAShop.request;

import java.math.BigDecimal;

import com.jonathan.JKNANAShop.model.Category;

import lombok.Data;

/**
 * @author JONATHAN
 */
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int invetory; //quantity
    private String description;
   
    private Category category;
    
    
}
