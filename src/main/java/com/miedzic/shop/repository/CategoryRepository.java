package com.miedzic.shop.repository;

import com.miedzic.shop.domain.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}