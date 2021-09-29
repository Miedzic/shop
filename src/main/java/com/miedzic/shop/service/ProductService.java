package com.miedzic.shop.service;

import com.miedzic.shop.domain.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);

    Product update(Product product, Long id);

    Product getById(Long id);

    void deleteById(Long id);

    Page<Product> getPage(Pageable pageable);

}
