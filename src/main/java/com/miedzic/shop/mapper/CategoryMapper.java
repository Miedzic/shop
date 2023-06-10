package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Category;
import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.domain.dto.CategoryDto;
import com.miedzic.shop.domain.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends AuditableMapper<CategoryDto, Category>  {
    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto category);
}
