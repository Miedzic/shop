package com.miedzic.shop.mapper;

import com.miedzic.shop.domain.dao.Auditable;
import com.miedzic.shop.domain.dto.AuditableDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface AuditableMapper<DTO extends AuditableDto,DAO extends Auditable>{
    @AfterMapping
    default void auditableMapping(@MappingTarget DTO.AuditableDtoBuilder<?,?> dto, DAO dao){

    }
}
