/**
 * 
 */
package com.jonathan.JKNANAShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonathan.JKNANAShop.model.Image;

/**
 * @author JONATHAN
 */
public interface ImageRepository extends JpaRepository<Image,Long> {

    /**
     * @param id
     * @return
     */
    List<Image> findByProductId(Long id);

}
