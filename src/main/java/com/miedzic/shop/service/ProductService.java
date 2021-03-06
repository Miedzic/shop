package com.miedzic.shop.service;

import com.miedzic.shop.domain.dao.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    @CachePut(cacheNames = "product",key = "#result.id")
    Product save(Product product, MultipartFile file);
    @CachePut(cacheNames = "product",key = "#result.id")
    Product update(Product product, Long id,MultipartFile file);
    @Cacheable(cacheNames = "product",key = "#id")
    Product getById(Long id);
    @CacheEvict(cacheNames = "product",key = "#id")
    void deleteById(Long id);

    Page<Product> getPage(Pageable pageable);

}
