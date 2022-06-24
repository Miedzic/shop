package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.CategoryDto;
import com.miedzic.shop.mapper.CategoryMapper;
import com.miedzic.shop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/controllers")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping("/{id}")
    public CategoryDto getProductById(@PathVariable Long id) {
        return categoryMapper.categoryToCategoryDto(categoryService.getById(id));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryDto saveProduct(@RequestBody CategoryDto category) {
        return categoryMapper.categoryToCategoryDto(categoryService.save(categoryMapper.categoryDtoToCategory(category)));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto updateProduct(@RequestBody CategoryDto category, @PathVariable Long id) {
        return categoryMapper.categoryToCategoryDto(categoryService.update(categoryMapper.categoryDtoToCategory(category), id));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }


    @GetMapping
    public Page<CategoryDto> getProductPage(@RequestParam int page, @RequestParam int size) {
        return categoryService.getPage(PageRequest.of(page, size)).map(categoryMapper::categoryToCategoryDto);
    }
}