package com.miedzic.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miedzic.shop.validator.PasswordValid;
import com.miedzic.shop.validator.group.Create;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@PasswordValid(groups = Create.class)
//@FirstnameValid
public class UserDto extends AuditableDto {
    @Null(groups = Create.class)
    private Long id;
    private Integer revisionNumber;
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @NotBlank(message = "last name cannot be blank")
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank(groups = Create.class)
    private String password;
    private boolean premium;
    private String confirmPassword;
}
