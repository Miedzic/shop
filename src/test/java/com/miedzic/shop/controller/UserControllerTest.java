package com.miedzic.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miedzic.shop.domain.dao.User;
import com.miedzic.shop.domain.dto.ErrorDto;
import com.miedzic.shop.domain.dto.UserDto;
import com.miedzic.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
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
                        .firstName("mati")
                        .lastName("d")
                        .email("matim98@tlen.pl")
                        .password("123")
                        .confirmPassword("123")
                        .premium(false)
                        .build())))
                .andExpect(status().isBadRequest());
    }
}
