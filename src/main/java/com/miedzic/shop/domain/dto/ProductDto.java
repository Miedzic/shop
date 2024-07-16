package com.miedzic.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miedzic.shop.validator.ProductValid;
import com.miedzic.shop.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@ProductValid
public class ProductDto  extends AuditableDto  {
    private Integer revisionNumber;
    @Null(groups = Create.class)
    private Long id;
    @NotBlank
    @Length(max = 100)
    private String name;
    @Length(max = 100)
    private String category;
    private Long cost;

    private String path;
}