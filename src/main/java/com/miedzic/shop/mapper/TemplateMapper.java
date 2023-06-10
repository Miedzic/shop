package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.domain.dao.Template;
import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper extends AuditableMapper<TemplateDto, Template>  {
    Template templateDtoToTemplate(TemplateDto dto);

    TemplateDto templateToTemplateDTO(Template template);

}
