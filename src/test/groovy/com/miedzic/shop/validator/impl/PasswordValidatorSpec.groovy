package com.miedzic.shop.validator.impl

import com.miedzic.shop.domain.dto.UserDto
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {
    def passwordValidator = new PasswordValidator()

    def 'Should test password validator'() {
        given:
        def userDto = new UserDto(password: password, confirmPassword: confirmedPassword)
        when:
        def result = passwordValidator.isValid(userDto, null)

        then:
        result == expected

        where:
        password | confirmedPassword || expected
        "matata" | "matat"           || false
        "matata" | "matata"          || true
    }
}
