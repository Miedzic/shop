package com.miedzic.shop.mapper

import com.miedzic.shop.domain.dao.Product
import com.miedzic.shop.domain.dto.ProductDto
import spock.lang.Specification

import java.time.LocalDateTime

class ProductMapperImplSpec extends Specification{
    def productMapperImpl = new ProductMapperImpl()

    def "Should convert product to productDto"(){
        given:
        def product = new Product(10,"szafa","meble",100, LocalDateTime.now(),"Mati",LocalDateTime.now(),"Mati","nope")
        when:
        def result = productMapperImpl.productToProductDto(product)
        then:
        result.id == product.id
        result.name == product.name
        result.category == product.category
        result.cost == product.cost

    }
    def "Should convert productDto to product"(){
        given:
        def productDTO = new ProductDto(10,10,"szafa","meble",100," ")
        when:
        def result = productMapperImpl.productDtoToProduct(productDTO)
        then:
        result.id == productDTO.id
        result.name == productDTO.name
        result.category == productDTO.category
        result.cost == productDTO.cost

    }
    def "should check if product is Null"(){
        given:
        def product = null
        when:
        def result = productMapperImpl.productToProductDto(product)
        then:
        result == null
    }
    def "should check if productDTO is Null"(){
        given:
        def productDTO = null
        when:
        def result = productMapperImpl.productDtoToProduct(productDTO)
        then:
        result == null
    }
}
