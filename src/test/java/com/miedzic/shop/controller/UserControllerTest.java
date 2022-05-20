package com.miedzic.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miedzic.shop.domain.dao.User;
import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("mati")
                                .lastName("d")
                                .email("matim98@tlen.pl")
                                .password("123")
                                .confirmPassword("123")
                                .premium(false)
                                .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.firstName").value("mati"))
                .andExpect(jsonPath("$.lastName").value("d"))
                .andExpect(jsonPath("$.email").value("matim98@tlen.pl"))
                .andExpect(jsonPath("$.premium").value(false))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist());

    }

    @Test
    void shouldNotSaveUserWhenAlreadyExist() throws Exception {
        userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("mati")
                                .lastName("d")
                                .email("matim98@tlen.pl")
                                .password("123")
                                .confirmPassword("123")
                                .premium(false)
                                .build())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("value not unique"));


    }

    @Test
    void shouldNotSaveUserWithIncorrectData() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("")
                                .lastName("")
                                .email("")
                                .password("123")
                                .confirmPassword("123")
                                .premium(false)
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", containsInAnyOrder("firstName", "lastName", "email")))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("last name cannot be blank", "must not be blank", "must not be blank")));
    }

    // save - password i confirm password różne
    @Test
    void shouldNotSaveUserWithIncorrectConfirmPassword() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserDto.builder()
                                .firstName("mateusz")
                                .lastName("dziedzic")
                                .email("matim98@tlen.pl")
                                .password("123")
                                .confirmPassword("1234")
                                .premium(false)
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("saveUser.user: Password and confirmed password are the same"));
    }

    @Test
    void shouldNotGetByIdUserWhenIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/users/956"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetByIdWhenUserIsAdmin() throws Exception {
        User user = userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.firstName").value("mati"))
                .andExpect(jsonPath("$.lastName").value("d"))
                .andExpect(jsonPath("$.email").value("matim98@tlen.pl"))
                .andExpect(jsonPath("$.premium").value(false));
    }

    @Test
    @WithMockUser(username = "matim98@tlen.pl")
    void shouldGetByIdWhenUserAskForHisData() throws Exception {
        User user = userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.firstName").value("mati"))
                .andExpect(jsonPath("$.lastName").value("d"))
                .andExpect(jsonPath("$.email").value("matim98@tlen.pl"))
                .andExpect(jsonPath("$.premium").value(false));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserPage() throws Exception {
        User user = userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        User user2 = userRepository.save(User.builder()
                .email("robert@wp.pl")
                .firstName("robert")
                .lastName("laweta")
                .password("234")
                .premium(true)
                .build());
        mockMvc.perform(get("/api/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value("2"))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(user.getId().intValue(),user2.getId().intValue())))
                .andExpect(jsonPath("$.content[*].password").doesNotExist())
                .andExpect(jsonPath("$.content[*].confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.content[*].firstName", containsInAnyOrder(user.getFirstName(), user2.getFirstName())))
                .andExpect(jsonPath("$.content[*].lastName", containsInAnyOrder(user.getLastName(), user2.getLastName())))
                .andExpect(jsonPath("$.content[*].email", containsInAnyOrder(user.getEmail(), user2.getEmail())))
                .andExpect(jsonPath("$.content[*].premium", containsInAnyOrder(user.isPremium(), user2.isPremium())));
    }
    @Test
    void shoulNotDeleteUserWhenIsNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/api/users/956"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteWhenUserIsAdmin() throws Exception {
        User user = userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        mockMvc.perform(delete("/api/users/" + user.getId()))
                //.contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.firstName").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.premium").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateWhenUserIsAdmin() throws Exception{
        User user = userRepository.save(User.builder()
                .email("matim98@tlen.pl")
                .firstName("mati")
                .lastName("d")
                .password("123")
                .premium(false)
                .build());
        mockMvc.perform(put("/api/users/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(UserDto.builder()
                        .firstName("mateusz")
                        .lastName("dziedzic")
                        .email("matim98@tlen.pl")
                        .password("123")
                        .confirmPassword("123")
                        .premium(false)
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.firstName").value("mateusz"))
                .andExpect(jsonPath("$.lastName").value("dziedzic"))
                .andExpect(jsonPath("$.email").value("matim98@tlen.pl"))
                .andExpect(jsonPath("$.premium").value(false));

    }
}

