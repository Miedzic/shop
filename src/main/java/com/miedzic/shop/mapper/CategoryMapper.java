package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Category;
import com.miedzic.shop.domain.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto category);
}
