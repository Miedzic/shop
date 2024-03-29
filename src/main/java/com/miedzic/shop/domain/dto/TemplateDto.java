package com.miedzic.shop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TemplateDto extends AuditableDto {
    private Long id;
    private String name;
    private String body;
    private String subject;
}
