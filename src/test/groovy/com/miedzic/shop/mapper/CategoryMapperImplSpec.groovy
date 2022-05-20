package com.miedzic.shop.mapper

import com.miedzic.shop.domain.dao.Category
import com.miedzic.shop.domain.dto.CategoryDto
import com.miedzic.shop.domain.dto.UserDto
import spock.lang.Specification

class CategoryMapperImplSpec extends Specification {
    def categoryMapperImpl = new CategoryMapperImpl()

    def "Should convert user to userDTO"() {
        given:
        def category = new Category(10, "meble", 15)

        when:
        def result = categoryMapperImpl.categoryToCategoryDto(category)

        then:
        result.id == category.id
        result.name == category.name
        result.numberOfProducts == category.numberOfProducts

    }

    def "Should convert userDto to user"() {
        given:
        def categoryDto = new CategoryDto(10,"meble",15)
        when:
        def result = categoryMapperImpl.categoryDtoToCategory(categoryDto)
        then:
        result.id == categoryDto.id
        result.name == categoryDto.name
        result.numberOfProducts == categoryDto.numberOfProducts

    }
    def "Should check if category is Null"() {
        given:
        def category = null
        when:
        def result = categoryMapperImpl.categoryToCategoryDto(category)
        then:
        result == null
    }
}
