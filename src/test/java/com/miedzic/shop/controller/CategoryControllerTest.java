package com.miedzic.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miedzic.shop.domain.dao.Category;
import com.miedzic.shop.domain.dto.CategoryDto;
import com.miedzic.shop.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @WithMockUser(authorities = "ADMIN")
    @Test
    void shouldSaveCategory() throws Exception{
        mockMvc.perform(post("/api/controllers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(CategoryDto.builder()
                        .name("meble")
                        .numberOfProducts(10)
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("meble"))
                .andExpect(jsonPath("$.numberOfProducts").value(10));

    }
    @Test
    void shouldGetCategoryById() throws Exception {
    Category category = categoryRepository.save(Category.builder()
            .name("meble")
            .numberOfProducts(10)
            .build());
    mockMvc.perform(get("/api/controllers/"+category.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(category.getId()))
            .andExpect(jsonPath("$.name").value("meble"))
            .andExpect(jsonPath("$.numberOfProducts").value(10));
    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    void shouldDeleteCategory() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("meble")
                .numberOfProducts(10)
                .build());
        mockMvc.perform(delete("/api/controllers/"+category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.numberOfProducts").doesNotExist());
    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    void shouldUpdateCategory() throws Exception{
        Category category = categoryRepository.save(Category.builder()
                .name("meble")
                .numberOfProducts(10)
                .build());
        mockMvc.perform(put("/api/controllers/"+category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(CategoryDto.builder()
                        .name("meble")
                        .numberOfProducts(10)
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value("meble"))
                .andExpect(jsonPath("$.numberOfProducts").value(10));
    }
}
