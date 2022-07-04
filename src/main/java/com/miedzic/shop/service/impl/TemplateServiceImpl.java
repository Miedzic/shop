package com.miedzic.shop.service.impl;

import com.miedzic.shop.domain.dao.Template;
import com.miedzic.shop.repository.TemplateRepository;
import com.miedzic.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;

    @Override
    public Template getTemplateByName(final String name) {
        return templateRepository.findByName(name);
    }

    @Override
    public void deleteById(final Long id) {
        templateRepository.deleteById(id);
    }

    @Override
    public Template update(final Template template, Long id) {
    Template templateDB = getById(id);
    templateDB.setId(template.getId());
    templateDB.setName(template.getName());
    templateDB.setSubject(template.getSubject());
    templateDB.setBody(template.getBody());
    return templateDB;
    }

    @Override
    public Template save(final Template template){
        return templateRepository.save(template);
    }

    @Override
    public Template getById(final Long id) {
        return templateRepository.getById(id);
    }
}
