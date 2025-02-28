/**
 * 
 */
package com.jonathan.JKNANAShop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.exception.product.ProductNotFoundException;
import com.jonathan.JKNANAShop.model.Category;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.repository.category.CategoryRepository;
import com.jonathan.JKNANAShop.repository.product.ProductRepository;
import com.jonathan.JKNANAShop.request.AddProductRequest;
import com.jonathan.JKNANAShop.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    
    @Override
    public Product addProduct(AddProductRequest request) {
	//check if the category is found in the DB
	//if yes, set it as new new product category
	//if no, save it as anew category then set it as the new product category
	Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName()))
		.orElseGet(() -> {
		    Category newCategory = new Category(request.getCategory().getName());
		    return categoryRepo.save(newCategory);
		});
	request.setCategory(category);
	
	return productRepo.save(createProduct(request, category));

    }
    
    private Product createProduct(AddProductRequest request, Category category) {
	return new Product(
		request.getName(),
		request.getBrand(),
		request.getPrice(),
		request.getInvetory(),
		request.getDescription(),
		category
		);
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
	return productRepo.findById(productId)
		.map(existinfProduct -> updateExistingProduct(existinfProduct,request))
		.map(productRepo::save)
		.orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }
    
    private Product updateExistingProduct(Product existinfProduct, ProductUpdateRequest request) {
	existinfProduct.setName(request.getName());
	existinfProduct.setBrand(request.getBrand());
	existinfProduct.setPrice(request.getPrice());
	existinfProduct.setInvetory(request.getInvetory());
	existinfProduct.setDescription(request.getDescription());
	
	Category category = categoryRepo.findByName(request.getCategory().getName());
	existinfProduct.setCategory(category);
	
	return existinfProduct;
    }

    @Override
    public Product getProductById(Long productId) {
	return productRepo.findById(productId)
		.orElseThrow(()->  new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(Long productId) {
	productRepo.findById(productId)
	.ifPresentOrElse(productRepo::delete,
		()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public List<Product> getAllProducts() {
	
	return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
	
	return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
	
	return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
	
	return productRepo.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
	return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
	
	return productRepo.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
	
	return productRepo.countByBrandAndName(brand,name);
    }

}
