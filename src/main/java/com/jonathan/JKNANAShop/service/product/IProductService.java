/**
 * 
 */
package com.jonathan.JKNANAShop.service.product;

import java.util.List;

import com.jonathan.JKNANAShop.dto.ProductDto;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.request.AddProductRequest;
import com.jonathan.JKNANAShop.request.ProductUpdateRequest;

/**
 * @author JONATHAN
 */
public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    Product getProductById(Long productId);
    void deleteProduct(Long productId);
    List<Product>getAllProducts();
    List<Product>getProductsByCategory(String category);
    List<Product>getProductsByBrand(String brand);
    List<Product>getProductsByCategoryAndBrand(String category, String brand);
    List<Product>getProductByName(String name);
    List<Product>getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand,String name);
    /**
     * @param product
     * @return
     */
    ProductDto convertToDto(Product product);
    /**
     * @param products
     * @return
     */
    List<ProductDto> getConvertedProducts(List<Product> products);
}
