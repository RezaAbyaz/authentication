package com.company.authentication;


import com.company.authentication.common.Encoders;
import com.company.authentication.common.JwtAuthenticationEntryPoint;
import com.company.authentication.common.JwtFilter;
import com.company.authentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Import(Encoders.class)
public class SecurityConfig  {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private JwtFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
