package com.miedzic.shop.service;

import com.miedzic.shop.domain.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);

    User update(User user, Long id);

    User getById(Long id);

    void deleteById(Long id);

    Page<User> getPage(Pageable pageable);

    User getCurrentUser();

    void confirmByToken(String token);
}
