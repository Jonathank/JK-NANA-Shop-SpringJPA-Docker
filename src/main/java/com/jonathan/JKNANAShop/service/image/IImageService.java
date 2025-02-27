/**
 * 
 */
package com.jonathan.JKNANAShop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jonathan.JKNANAShop.dto.ImageDto;
import com.jonathan.JKNANAShop.model.Image;

/**
 * @author JONATHAN
 */
public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List< ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
