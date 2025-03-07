/**
 * 
 */
package com.jonathan.JKNANAShop.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.jonathan.JKNANAShop.dto.ImageDto;
import com.jonathan.JKNANAShop.dto.ProductDto;
import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Category;
import com.jonathan.JKNANAShop.model.Image;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.repository.CategoryRepository;
import com.jonathan.JKNANAShop.repository.ImageRepository;
import com.jonathan.JKNANAShop.repository.ProductRepository;
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
    private final ImageRepository imageRepo;
    private final ModelMapper modelMapper;
    
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
	
	return  productRepo.findAllBy();
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
    
    @Override
    public List<ProductDto> getConvertedProducts(List<Product>products){
	return products.stream().map(this::convertToDto).toList();
    }
    
    @Override
    public ProductDto convertToDto(Product product) {
	ProductDto dto = modelMapper.map(product, ProductDto.class);
	List<Image> images = imageRepo.findByProductId(product.getId());
	List<ImageDto> imageDtos = images.stream().map(image -> {
            ImageDto imageDto = new ImageDto();
           // imageDto.setImageId(image.getId());
            imageDto.setImageName(image.getFileName());
            imageDto.setDownloadUrl(image.getDownloadiUrl());  // Ensure this is not null
            return imageDto;
        }).collect(Collectors.toList());

        dto.setImages(imageDtos);
        return dto;
    }

}
