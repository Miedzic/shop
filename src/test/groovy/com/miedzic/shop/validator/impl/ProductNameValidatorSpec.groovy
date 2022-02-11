package com.miedzic.shop.validator.impl

import com.miedzic.shop.domain.dto.ProductDto
import spock.lang.Specification

class ProductNameValidatorSpec extends Specification{
    def productValidator = new ProductNameValidator()

    def "check if product name is the same as it's category name"(){
        given:
        def productDto = new ProductDto(name:name,category: category)

        when:
        def result = productValidator.isValid(productDto,null)

        then:
        result == expected

        where:
        name|category||expected
        "szafa"|"meble"|| true
        "szafa"|"szafa"|| false
    }
}
