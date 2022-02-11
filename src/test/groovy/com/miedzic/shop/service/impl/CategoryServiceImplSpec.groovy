package com.miedzic.shop.service.impl

import com.miedzic.shop.domain.dao.Category
import com.miedzic.shop.repository.CategoryRepository
import spock.lang.Specification

class CategoryServiceImplSpec extends Specification{
    def categoryRepository = Mock(CategoryRepository)
    def categoryService = new CategoryServiceImpl(categoryRepository)

    def 'Should save category '(){
        given:
        def category = Mock(Category)
        when:
        categoryService.save(category)
        then:
        1* categoryRepository.save(category)
    }
}
