package com.miedzic.shop.validator.impl

import com.miedzic.shop.domain.dto.UserDto
import spock.lang.Specification

class FirstnameLengthValidatorSpec extends Specification{
    def length = new FirstnameLengthValidator()

    def 'should test firstname length validator'(){
        given:
        def userDto = new UserDto(firstName: firstName)
        when:
        def result = length.isValid(userDto,null)
        then:
        result == expected
        where:
        firstName|expected
        "xdxdxd" | true
        "abcdabcd"|true
    }
}
