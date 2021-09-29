package com.miedzic.shop.service;

import com.miedzic.shop.domain.dao.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category save(Category category);

    Category update(Category category, Long id);

    Category getById(Long id);

    void deleteById(Long id);

    Page<Category> getPage(Pageable pageable);
}
