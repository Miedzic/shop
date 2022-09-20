package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Template;
import com.miedzic.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template templateDtoToTemplate(TemplateDto dto);

    TemplateDto templateToTemplateDTO(Template template);

}
