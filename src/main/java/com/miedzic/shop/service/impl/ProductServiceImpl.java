package com.miedzic.shop.service.impl;

import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.repository.ProductRepository;
import com.miedzic.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.logging.Logger;
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public Product save(final Product product) {
        //jeśli jest id to robi selecta sprawdzającego czy obiekt w bazie istnieje po tym id, update/insert
        return productRepository.save(product);
    }

    @Override
    @Transactional
    // dirty update, jeśli obiekt z bazy danych zostanie zaaktualizowany w jakiś sposób to po wykonaniu funkcji zostanie zaaktualizowany w bazie danych
    public Product update(final Product product, final Long id) {
        Product userDb = getById(id);
        userDb.setName(product.getName());
        userDb.setCategory(product.getCategory());
        userDb.setCost(product.getCost());
        return userDb;
    }

    @Override
    public Product getById(final Long id) {
        log.info("Product not in Cache {}",id);
        return productRepository.getById(id);
    }

    @Override
    public void deleteById(final Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getPage(final Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
