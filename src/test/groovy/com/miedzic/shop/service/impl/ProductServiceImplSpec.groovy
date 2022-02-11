package com.miedzic.shop.service.impl

import com.miedzic.shop.config.properties.FilePropertiesConfig
import com.miedzic.shop.domain.dao.Product
import com.miedzic.shop.helper.FileHelper
import com.miedzic.shop.repository.ProductRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Path

class ProductServiceImplSpec extends Specification {
    def productRepository = Mock(ProductRepository)
    def filePropertiesConfig = Mock(FilePropertiesConfig)
    def fileHelper = Mock(FileHelper)
    def productService = new ProductServiceImpl(productRepository, filePropertiesConfig, fileHelper)

    def 'Should save product'() {
        given:
        def product = Mock(Product)
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)

        when:
        productService.save(product, file)

        then:
        1 * productRepository.save(product)
        1 * file.getOriginalFilename() >> "szafa.webp"
        1 * file.getInputStream() >> inputStream
        1 * filePropertiesConfig.getProduct() >> "abcd"
        1 * product.getName() >> "szafa"
        1 * fileHelper.fileCopy(inputStream, _ as Path)
        1 * product.setPath("abcd\\szafa.webp")
        1 * productRepository.save(product)
        0 * _
    }

    def 'Should get by id'() {
        given:
        def id = 10L

        when:
        productService.getById(id)

        then:
        1 * productRepository.getById(id)
        0 * _
    }

    def 'Should delete item by id'() {
        given:
        def id = 10L

        when:
        productService.deleteById(id)

        then:
        1 * productRepository.deleteById(id)
        0 * _
    }

    def 'Should get Page'() {
        given:
        def pageable = Mock(Pageable)

        when:
        productService.getPage(pageable)

        then:
        1 * productRepository.findAll(pageable)
        0 * _
    }
}
