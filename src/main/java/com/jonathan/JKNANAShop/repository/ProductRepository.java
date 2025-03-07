/**
 * 
 */
package com.jonathan.JKNANAShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonathan.JKNANAShop.model.Product;

/**
 * @author JONATHAN
 */
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    /**
     * @param category
     * @return
     */
    List<Product> findByCategoryName(String category);

    /**
     * @param brand
     * @return
     */
    List<Product> findByBrand(String brand);

    /**
     * @param category
     * @param brand
     * @return
     */
    List<Product> findByCategoryNameAndBrand(String category, String brand);

    /**
     * @param name
     * @return
     */
    List<Product> findByName(String name);

    /**
     * @param brand
     * @param name
     * @return
     */
    List<Product> findByBrandAndName(String brand, String name);

    /**
     * @param brand
     * @param name
     * @return
     */
    Long countByBrandAndName(String brand, String name);
    /**
     * @return
     */
    List<Product> findAllBy();
    

}
