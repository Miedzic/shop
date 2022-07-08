package com.miedzic.shop.controller;

import com.miedzic.shop.domain.dto.LoginDto;
import com.miedzic.shop.domain.dto.TokenDto;
import com.miedzic.shop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @PostMapping
    public TokenDto Login(@RequestBody LoginDto loginDto){
        return loginService.login(loginDto.getEmail(),loginDto.getPassword());
    }

}
