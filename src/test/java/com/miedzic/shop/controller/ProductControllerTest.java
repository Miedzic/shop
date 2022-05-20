package com.miedzic.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miedzic.shop.domain.dao.Product;
import com.miedzic.shop.domain.dto.ProductDto;
import com.miedzic.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "plik.png", MediaType.APPLICATION_OCTET_STREAM_VALUE, new byte[0]);
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("szafa")
                .category("meble")
                .cost(100L)
                .build()));
        mockMvc.perform(multipart("/api/products")
                        .file(file)
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("szafa"))
                .andExpect(jsonPath("$.category").value("meble"))
                .andExpect(jsonPath("$.cost").value(100L))
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.path").value("target\\szafa.png"));



    }

    @Test
    void shouldNotSaveProductWithoutImage() throws Exception {
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("szafa")
                .category("meble")
                .cost(100L)
                .build()));
        mockMvc.perform(multipart("/api/products")
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Required request part 'file' is not present"));
    }

    @Test
    void shouldNotSaveProductWhenIncorrectData() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "plik.csv", MediaType.APPLICATION_OCTET_STREAM_VALUE, new byte[0]);
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("")
                .category("meblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblem" +
                        "eblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeble" +
                        "meblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblem" +
                        "eblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemebleme" +
                        "blemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemebl" +
                        "emeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeblemeble")
                .cost(100L)
                .build()));
        mockMvc.perform(multipart("/api/products")
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", containsInAnyOrder("name", "category")))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("must not be blank", "length must be between 0 and 100")));
    }

    @Test
    void shouldNotSaveProductWithInvalidExtension() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "plik.xds", MediaType.APPLICATION_OCTET_STREAM_VALUE, new byte[0]);
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("fafga")
                .category("afaa")
                .cost(100L)
                .build()));
        mockMvc.perform(multipart("/api/products")
                        .file(file)
                        .file(product)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("saveProduct.file: Wrong file extension!"));
    }
}
