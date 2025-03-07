/**
 * 
 */
package com.jonathan.JKNANAShop.dto;

import java.math.BigDecimal;
import java.util.List;

import com.jonathan.JKNANAShop.model.Category;

import lombok.Data;

/**
 * @author JONATHAN
 */
@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int invetory; //quantity
    private String description;
    private Category category;
    private List<ImageDto>images;
}
