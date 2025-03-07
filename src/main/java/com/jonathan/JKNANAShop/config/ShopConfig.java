/**
 * 
 */
package com.jonathan.JKNANAShop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JONATHAN
 */
@Configuration
public class ShopConfig {

    @Bean
    ModelMapper modelMapper() {
    return new ModelMapper();
}
}