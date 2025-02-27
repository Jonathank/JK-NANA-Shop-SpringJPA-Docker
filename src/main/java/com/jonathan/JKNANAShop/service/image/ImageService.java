/**
 * 
 */
package com.jonathan.JKNANAShop.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jonathan.JKNANAShop.dto.ImageDto;
import com.jonathan.JKNANAShop.exception.ResourceNotFoundException;
import com.jonathan.JKNANAShop.model.Image;
import com.jonathan.JKNANAShop.model.Product;
import com.jonathan.JKNANAShop.repository.image.ImageRepository;
import com.jonathan.JKNANAShop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

/**
 * @author JONATHAN
 */
@Service
@RequiredArgsConstructor 
public class ImageService implements IImageService{

    private final ImageRepository imageRepo;
    private final IProductService productService;
    
    @Override
    public Image getImageById(Long id) {
	
	return imageRepo.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("No Image found with id "+ id));
    }

    @Override
    public void deleteImageById(Long id) {
	imageRepo.findById(id).ifPresentOrElse(imageRepo::delete, ()->{
	    throw new ResourceNotFoundException("No Image found with id "+ id);
	});
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
	Product product = productService.getProductById(productId);
	List<ImageDto> savedImageDtoList = new ArrayList<>();
	
	for(MultipartFile file : files) {
	    try {
		Image image = new Image();
		image.setFileName(file.getOriginalFilename());
		image.setFileType(file.getContentType());
		image.setImage(new SerialBlob(file.getBytes()));
		image.setProduct(product);

		String BuildDownloadurl = "/api/v1/images/image/download/";
		//return url for image download
		String downLoadUrl = BuildDownloadurl+image.getId();
		image.setDownloadiUrl(downLoadUrl);
		
		Image savedImage = imageRepo.save(image);
		//update url
		savedImage.setDownloadiUrl(BuildDownloadurl +savedImage.getId());
		imageRepo.save(savedImage);
		
		ImageDto imageDto = new ImageDto();
		imageDto.setImageId(savedImage.getId());
		imageDto.setImageName(savedImage.getFileName());
		imageDto.setDownloadUrl(savedImage.getDownloadiUrl());
		
		savedImageDtoList.add(imageDto);
		
	    }catch(IOException |SQLException e) {
		throw new RuntimeException(e.getMessage());
	    }
	}
	return savedImageDtoList;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
	Image image = getImageById(imageId);
	try {
	image.setFileName(file.getOriginalFilename());
	image.setFileType(file.getContentType());
	image.setImage(new SerialBlob(file.getBytes()));
	imageRepo.save(image);
	}catch(IOException | SQLException e) {
	    throw new RuntimeException(e.getMessage());
	}
	
    }

    
}
