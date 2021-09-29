package com.miedzic.shop.service.impl;

import com.miedzic.shop.domain.dao.Category;
import com.miedzic.shop.repository.CategoryRepository;
import com.miedzic.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public Category save(final Category category) {
        //jeśli jest id to robi selecta sprawdzającego czy obiekt w bazie istnieje po tym id, update/insert
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    // dirty update, jeśli obiekt z bazy danych zostanie zaaktualizowany w jakiś sposób to po wykonaniu funkcji zostanie zaaktualizowany w bazie danych
    public Category update(final Category category, final Long id) {
        Category userDb = getById(id);
        userDb.setName(category.getName());
        userDb.setNumberOfProducts(category.getNumberOfProducts());
        return userDb;
    }

    @Override
    public Category getById(final Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public void deleteById(final Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getPage(final Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
