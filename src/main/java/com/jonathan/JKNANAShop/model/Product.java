/**
 * 
 */
package com.jonathan.JKNANAShop.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JONATHAN
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int invetory; //quantity
    private String description;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="category_id")
    private Category category;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image>images;

    /**
     * @param name
     * @param brand
     * @param price
     * @param invetory
     * @param description
     * @param category
     */
    public Product(String name, String brand, BigDecimal price, int invetory, String description, Category category) {
	this.name = name;
	this.brand = brand;
	this.price = price;
	this.invetory = invetory;
	this.description = description;
	this.category = category;
    }
    
    
    
}
