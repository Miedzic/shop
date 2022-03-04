package com.miedzic.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miedzic.shop.validator.ExtensionValid;
import com.miedzic.shop.validator.ProductValid;
import com.miedzic.shop.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ProductValid
public class ProductDto {
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