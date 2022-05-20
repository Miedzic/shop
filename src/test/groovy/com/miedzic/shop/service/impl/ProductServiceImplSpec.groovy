package com.miedzic.shop.service.impl

import com.miedzic.shop.config.properties.FilePropertiesConfig
import com.miedzic.shop.domain.dao.Product
import com.miedzic.shop.helper.FileHelper
import com.miedzic.shop.repository.ProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Path
import java.time.LocalDateTime

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

    def 'Should update product'() {
        given:
        def product = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "szafa.wepb")
        def id = 10;
        def productDB = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "szafa1.wepb")
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = "abcd\\szafa.webp"

        when:
        def result = productService.update(product, id, file)

        then:
        1 * productRepository.getById(id) >> productDB
        1 * file.getOriginalFilename() >> "szafa.webp"
        1 * filePropertiesConfig.getProduct() >> "abcd"
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.fileCopy(inputStream, _ as Path)//path
        //  1 * result.setPath(path.toString())
        1 * fileHelper.fileDelete(_ as Path)
        0 * _

        result.id == product.id
        result.name == product.name
        result.category == product.category
        result.cost == product.cost
        result.path == path

    }

    def "Should not delete file name when updating product"() {
        given:
        def product = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "abcd\\szafa.webp")
        def id = 10;
        def productDB = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "abcd\\szafa.webp")
        def file = Mock(MultipartFile)
        def inputStream = Mock(InputStream)
        def path = "abcd\\szafa.webp"

        when:
        def result = productService.update(product, id, file)

        then:
        1 * productRepository.getById(id) >> productDB
        1 * file.getOriginalFilename() >> "szafa.webp"
        1 * filePropertiesConfig.getProduct() >> "abcd"
        1 * file.getInputStream() >> inputStream
        1 * fileHelper.fileCopy(inputStream, _ as Path)//path
        0 * _

        result.id == product.id
        result.name == product.name
        result.category == product.category
        result.cost == product.cost
        result.path == path

    }

    def "Should throw IoException when updating product"() {
        given:
        def product = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "abcd\\szafa.webp")
        def id = 10;
        def productDB = new Product(10, "szafa", "meble", 100, LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati", "abcd\\szafa.webp")
        def file = Mock(MultipartFile)
        def path = "abcd\\szafa.webp"

        when:
        def result = productService.update(product, id, file)

        then:
        1 * productRepository.getById(id) >> productDB
        1 * file.getOriginalFilename() >> "szafa.webp"
        1 * filePropertiesConfig.getProduct() >> "abcd"
        1 * file.getInputStream() >> { throw new IOException() }
        0 * _

        result.id == product.id
        result.name == product.name
        result.category == product.category
        result.cost == product.cost
        result.path == path
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
