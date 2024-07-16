package com.miedzic.shop.domain.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorDto {
    private String field;
    private String message;
}
