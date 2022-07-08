package com.miedzic.shop.service;

import com.miedzic.shop.domain.dto.TokenDto;

public interface LoginService {
     TokenDto login(String email, String password);
}
