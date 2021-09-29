package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);
}
