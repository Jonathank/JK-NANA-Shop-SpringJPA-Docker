/**
 * 
 */
package com.jonathan.JKNANAShop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jonathan.JKNANAShop.exception.AlreadyExistException;
import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Category;
import com.jonathan.JKNANAShop.repository.category.CategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepo;
    
    @Override
    public Category getCategoryById(Long id) {
	
	return categoryRepo.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	
    }

    @Override
    public Category getCategoryByName(String name) {
	return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategorys() {
	return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
	
	return Optional.of(category).filter(c -> !categoryRepo.existsByName(c.getName()))
		.map(categoryRepo::save).orElseThrow(() -> new AlreadyExistException(category.getName() +"already exist"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
	
	return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{
	    oldCategory.setName(category.getName());
	    return categoryRepo.save(oldCategory);
	}).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
	categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete, ()->{
	    throw new ResourceNotFoundException("Category not found!");
	});
	
    }

}
