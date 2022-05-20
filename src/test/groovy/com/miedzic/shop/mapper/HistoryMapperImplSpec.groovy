package com.miedzic.shop.mapper

import com.miedzic.shop.domain.dao.Product
import com.miedzic.shop.domain.dao.Role
import com.miedzic.shop.domain.dao.User
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import spock.lang.Specification

import java.time.LocalDateTime

class HistoryMapperImplSpec extends Specification {
    def historyMapperImpl = new HistoryMapperImpl()


    def "should make revision to UserDto"() {
        given:
        def user = new User(10, "Mateusz", "Dziedzic", "matim98@tlen.pl", "123456", true, [new Role(1, "coś")], LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati")
        def revision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity()), user)

        when:
        def result = historyMapperImpl.revisionToUserDto(revision)

        then:
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.password == null
        result.premium == user.premium
        result.revisionNumber == revision.requiredRevisionNumber
    }
    //todo drugie
    def "should make revision to ProductDto"(){
        given:
        def product = new Product(10,"szafa","meble",100, LocalDateTime.now(),"Mati",LocalDateTime.now(),"Mati","nope")
        def revision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity()),product)
        when:
        def result = historyMapperImpl.revisionToProductDto(revision)
        then:
        result.id == product.id
        result.name == product.name
        result.category == product.category
        result.cost == product.cost
        result.revisionNumber == revision.requiredRevisionNumber
    }
    def "Should check if revision is Null"() {
        given:
        def revision = null
        when:
        def result = historyMapperImpl.revisionToProductDto(revision)
        then:
        result == null
    }
}
