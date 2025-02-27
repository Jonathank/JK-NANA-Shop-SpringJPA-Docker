/**
 * 
 */
package com.jonathan.JKNANAShop.service.category;

import java.util.List;

import com.jonathan.JKNANAShop.model.Category;

/**
 * @author JONATHAN
 */
public interface ICategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategorys();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
    
    
}
