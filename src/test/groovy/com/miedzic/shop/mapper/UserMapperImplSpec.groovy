package com.miedzic.shop.mapper

import com.miedzic.shop.domain.dao.Role
import com.miedzic.shop.domain.dao.User
import com.miedzic.shop.domain.dto.UserDto
import spock.lang.Specification

import java.time.LocalDateTime

class UserMapperImplSpec extends Specification {
    def userMapperImpl = new UserMapperImpl()

    def "Should convert user to userDTO"() {
        given:
        def user = new User(10, "Mateusz", "Dziedzic", "matim98@tlen.pl", "123456", true, [new Role(1, "coś")], LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati")

        when:
        def result = userMapperImpl.userToUserDto(user)

        then:
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.password == null
        result.premium == user.premium
        result.revisionNumber == null

    }

    def "Should convert userDto to user"() {
        given:
        def userDto = new UserDto(10,10,"Mateusz","Dziedzic","matim98@tlen.pl","123456",true,"123456")
        when:
        def result = userMapperImpl.userDtoToUser(userDto)
        then:
        result.id == userDto.id
        result.password == userDto.password
        result.firstName == userDto.firstName
        result.lastName == userDto.lastName
        result.email == userDto.email
        result.premium == userDto.premium
    }
   /* def "Should confirm password"(){
        given:
        def userDto = new UserDto(10,10,"Mateusz","Dziedzic","matim98@tlen.pl","123456",true,"123456")
        expect:
        userDto.password == userDto.confirmPassword
    }*/
    def "Should check if user is Null"() {
        given:
        def user = null
        when:
        def result = userMapperImpl.userToUserDto(user)
        then:
        result == null
    }
}
