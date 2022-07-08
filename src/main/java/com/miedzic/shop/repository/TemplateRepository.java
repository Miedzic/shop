package com.miedzic.shop.repository;

import com.miedzic.shop.domain.dao.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TemplateRepository extends JpaRepository<Template, Long> {
    Template findByName(String Name);
}
