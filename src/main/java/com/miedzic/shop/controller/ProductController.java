package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.mapper.ProductMapper;
import com.miedzic.shop.service.ProductService;
import com.miedzic.shop.validator.group.Create;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @PostMapping
    @Validated(Create.class)
    public ProductDto saveProduct(@RequestPart @Valid ProductDto product, @RequestPart MultipartFile file) {

        return productMapper.productToProductDto(productService.save(productMapper.productDtoToProduct(product), file));
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestPart @Valid ProductDto product, @PathVariable Long id, @RequestPart MultipartFile file) {
        return productMapper.productToProductDto(productService.update(productMapper.productDtoToProduct(product), id, file));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public Page<ProductDto> getProductPage(@RequestParam int page, @RequestParam int size) {
        return productService.getPage(PageRequest.of(page, size)).map(productMapper::productToProductDto);
    }
}
