package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Template;
import com.miedzic.shop.domain.dto.TemplateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template templateDtoToTemplate(TemplateDTO dto);

    TemplateDTO templateToTemplateDTO(Template template);

}
