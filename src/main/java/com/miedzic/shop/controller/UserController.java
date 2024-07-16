package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dao.FieldErrorDto;
import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.mapper.UserMapper;
import com.miedzic.shop.service.UserService;
import com.miedzic.shop.validator.group.Create;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("isAuthenticated() && (hasAuthority('ADMIN') || @securityService.hasAccessToUser(#id))")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.userToUserDto(userService.getById(id));
    }


    @PostMapping
    @Validated(Create.class)
    public UserDto saveUser(@RequestBody @Valid UserDto user) {
        return userMapper.userToUserDto(userService.save(userMapper.userDtoToUser(user)));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("isAuthenticated() && (hasAuthority('ADMIN') || @securityService.hasAccessToUser(#id))")
    // hasAnyRole / isAnonymous
    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody @Valid UserDto user, @PathVariable Long id) {
        return userMapper.userToUserDto(userService.update(userMapper.userDtoToUser(user), id));
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @Operation(security = @SecurityRequirement(name = "bearer token"))
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public Page<UserDto> getUserPage(@RequestParam int page, @RequestParam int size) {
        return userService.getPage(PageRequest.of(page, size)).map(userMapper::userToUserDto);
    }
    @GetMapping("/confirm")
    public void confirmUser(@RequestParam String token ){
        userService.confirmByToken(token);
    }

    @PostMapping("/test")
    public void test(@RequestBody FieldErrorDto fieldErrorDto){

    }
    //some weird magic :?
}