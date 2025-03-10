/**
 * 
 */
package com.jonathan.JKNANAShop.model;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JONATHAN
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    //@JsonIgnore
    private Blob image;
    private String downloadiUrl;
    
    @ManyToOne
    @JoinColumn(name = "product_id")

    private Product product;
    
}
