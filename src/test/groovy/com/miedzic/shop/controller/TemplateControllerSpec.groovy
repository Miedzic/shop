package com.miedzic.shop.controller

import com.miedzic.shop.domain.dao.Template
import com.miedzic.shop.repository.TemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class TemplateControllerSpec extends Specification {
    @Autowired
    private MockMvc mockMvc
    @Autowired
    private TemplateRepository templateRepository


    def "should get Template By Id"() {
        given:
        def template = templateRepository.save(Template.builder()
                .name("name1")
                .body("body1")
                .subject("subject1")
                .build())


        expect:
        mockMvc.perform(get("/api/templates/" + template.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(template.id))
                .andExpect(jsonPath('$.name').value(template.name))
                .andExpect(jsonPath('$.body').value(template.body))
                .andExpect(jsonPath('$.subject').value(template.subject))

    }


}
