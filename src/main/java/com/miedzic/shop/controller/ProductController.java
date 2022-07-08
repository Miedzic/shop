package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.mapper.ProductMapper;
import com.miedzic.shop.service.ProductService;
import com.miedzic.shop.validator.ExtensionValid;
import com.miedzic.shop.validator.group.Create;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;


    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productMapper.productToProductDto(productService.getById(id));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Validated(Create.class)
    public ProductDto saveProduct(@RequestPart @Valid ProductDto product, @RequestPart @Valid @ExtensionValid(groups = Create.class) MultipartFile file) {

        return productMapper.productToProductDto(productService.save(productMapper.productDtoToProduct(product), file));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestPart @Valid ProductDto product, @PathVariable Long id, @RequestPart @Valid @ExtensionValid(groups = Create.class) MultipartFile file) {
        return productMapper.productToProductDto(productService.update(productMapper.productDtoToProduct(product), id, file));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public Page<ProductDto> getProductPage(@RequestParam int page, @RequestParam int size) {
        return productService.getPage(PageRequest.of(page, size)).map(productMapper::productToProductDto);
    }
}
