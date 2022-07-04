package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.TemplateDTO;
import com.miedzic.shop.mapper.TemplateMapper;
import com.miedzic.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @GetMapping("/{id}")
    public TemplateDTO getTemplateById(@PathVariable Long id){
        return templateMapper.templateToTemplateDTO(templateService.getById(id));
    }
    @PostMapping
    public TemplateDTO saveTemplate(@RequestBody TemplateDTO template){
        return templateMapper.templateToTemplateDTO(templateService.save(templateMapper.templateDtoToTemplate(template)));
    }
    @PutMapping("/{id}")
    public TemplateDTO updateTemplate(@RequestBody TemplateDTO template,@PathVariable Long id){
        return templateMapper.templateToTemplateDTO(templateService.update(templateMapper.templateDtoToTemplate(template), id));
    }
    @DeleteMapping("/{id}")
    public void deleteTemplateById(@PathVariable Long id){
        templateService.deleteById(id);
    }
}
