package com.miedzic.shop.service;

import com.miedzic.shop.domain.dao.Template;

import java.util.Optional;

public interface TemplateService {
    Template getTemplateByName(String name);

    void deleteById(Long id);

    Template update(Template template,  Long id);

    Template save(Template template);

    Template getById(Long id);


}
