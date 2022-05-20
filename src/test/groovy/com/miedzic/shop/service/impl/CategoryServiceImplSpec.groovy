package com.miedzic.shop.service.impl

import com.miedzic.shop.domain.dao.Category
import com.miedzic.shop.repository.CategoryRepository
import org.springframework.data.domain.Pageable
import spock.lang.Specification


class CategoryServiceImplSpec extends Specification {
    def categoryRepository = Mock(CategoryRepository)
    def categoryService = new CategoryServiceImpl(categoryRepository)
    def 'Should get category by id'() {
        given:
        def id = 1L

        when:
        categoryService.getById(id)

        then:
        1 * categoryRepository.getById(id)
        0 * _
    }
    def 'Should delete category by id'(){
        given:
        def id = 2L

        when:
        categoryService.deleteById(id)

        then:
        1 * categoryRepository.deleteById(id)
        0 * _
    }
    def 'Should save category '() {
        given:
        def category = Mock(Category)
        when:
        categoryService.save(category)
        then:
        1 * categoryRepository.save(category)
    }

    def 'Should update category'() {
        given:
        def category = new Category(10, "meble", 5)
        def id = 10;
        def categoryDB = new Category(10, "meble", 6)
        categoryRepository.getById(id) >> categoryDB

        when:
        def result = categoryService.update(category, id)

        then:
        result.id == category.id
        result.name == category.name
        result.numberOfProducts == category.numberOfProducts
    }

    def 'Should get page'(){
        given:
        def page = Mock(Pageable)

        when:
        categoryService.getPage(page)

        then:
        1 * categoryRepository.findAll(page)
        0 * _
    }
}
