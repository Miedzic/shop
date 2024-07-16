package com.miedzic.shop.service.impl

import com.miedzic.shop.domain.dao.Role
import com.miedzic.shop.domain.dao.User
import com.miedzic.shop.repository.RoleRepository
import com.miedzic.shop.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import jakarta.persistence.EntityNotFoundException
import java.time.LocalDateTime

class UserServiceImplSpec extends Specification {
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder)

    def 'Should get user by id'() {
        given:
        def id = 1L

        when:
        userService.getById(id)

        then:
        1 * userRepository.getById(id)
        0 * _
    }

    def 'Should delete user by id'() {
        given:
        def id = 2L

        when:
        userService.deleteById(id)

        then:
        1 * userRepository.deleteById(id)
        0 * _
    }

    def "Should save user"() {
        given:
        def user = Mock(User)
        def role = new Role()
        when:
        userService.save(user)

        then:
        1 * user.getPassword() >> "staszek"
        1 * passwordEncoder.encode("staszek") >> "abc"
        1 * user.setPassword("abc")
        1 * roleRepository.findByName("ROLE_USER") >> Optional.of(role)
        1 * user.setRoles(Collections.singletonList(role))
        1 * userRepository.save(user)
        0 * _
    }

    def "Should update user"() {
        given:
        def user = new User(10, "Mateusz", "Dziedzic", "matim98@tlen.pl", "123456", true, [new Role(1, "coś")], LocalDateTime.now(), "Mati", LocalDateTime.now(), "Mati")
        def id = 10
        def userDB = new User(10, "Robert", "Krędzic", "falafel@tlen.pl", "543145", true, [new Role(1, "ktoś")], LocalDateTime.now(), "Robert", LocalDateTime.now(), "Robert")
        userRepository.getById(id) >> userDB

        when:
        def result = userService.update(user, id)

        then:
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.password == userDB.password
        result.premium == user.premium
    }
    def "Should get current User"(){
        given:
        def authentication = Mock(Authentication)
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> "Mati"
        userRepository.findByEmail("Mati") >> Optional.empty()

        when:
        userService.getCurrentUser()


        then:
        thrown EntityNotFoundException
    }
    def "Should get page"() {
        given:
        def page = Mock(Pageable)

        when:
        userService.getPage(page)

        then:
        1 * userRepository.findAll(page)
        0 * _
    }
}
