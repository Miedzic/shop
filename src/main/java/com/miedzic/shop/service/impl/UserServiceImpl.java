package com.miedzic.shop.service.impl;

import com.miedzic.shop.domain.dao.User;
import com.miedzic.shop.repository.RoleRepository;
import com.miedzic.shop.repository.UserRepository;
import com.miedzic.shop.security.SecurityUtils;
import com.miedzic.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailServiceImpl mailService;


    @Override
    public User save(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Collections.singletonList(role))); //zawsze musimy prefix + docelowa nazwa roli ROLE_[rola]
        user.setConfirmationToken(UUID.randomUUID().toString());
        userRepository.save(user);
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("url", "http://localhost:5000/api/users/confirm?token=" + user.getConfirmationToken());
        mailService.sendEmail(user.getEmail(), "register user confirmation", map, null, null);
        //jeśli jest id to robi selecta sprawdzającego czy obiekt w bazie istnieje po tym id, update/insert
        return user;

    }

    @Override
    @Transactional
    // dirty update, jeśli obiekt z bazy danych zostanie zaaktualizowany w jakiś sposób to po wykonaniu funkcji zostanie zaaktualizowany w bazie danych
    public User update(final User user, final Long id) {
        var userDb = getById(id);
        userDb.setEmail(user.getEmail());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setPremium(user.isPremium());
        return userDb;
    }

    @Override
    public User getById(final Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getPage(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void confirmByToken(final String token) {
        User user = userRepository.findByConfirmationToken(token).orElseThrow(EntityNotFoundException::new);
        user.setConfirmationToken(null);

    }

}
