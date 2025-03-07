/**
 * 
 */
package com.jonathan.JKNANAShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonathan.JKNANAShop.model.Category;

/**
 * 
 */
public interface CategoryRepository extends JpaRepository<Category,Long>{

    /**
     * @param name
     * @return
     */
    Category findByName(String name);

    /**
     * @param name
     * @return
     */
    boolean existsByName(String name);

}
