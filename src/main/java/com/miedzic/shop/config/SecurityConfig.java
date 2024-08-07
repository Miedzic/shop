/*
package com.miedzic.shop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
@RequiredArgsConstructor
@Deprecated
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()  // token defaultowy security , blokuje end pointy w springu (nic nie działa)
                .ignoringAntMatchers("/**")  // wyłączamy csrf na wszystkich endpointach (jednak działa)
                .and()
                .cors() // gdy inna apka jest z innego adresu, to nie może się komunikować bez pozwolenia z naszą apką
                .and()
               // .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper))
           //DO POPRAWY BEZWZGLĘDNIE USUNĄłem to sobie  //   .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //aplikacja bezstanowa
    }
}
*/
