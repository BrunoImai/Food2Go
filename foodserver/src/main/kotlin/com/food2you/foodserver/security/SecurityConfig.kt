package com.food2you.foodserver.security

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfig(private val jwtTokenFilter: JwtTokenFilter) {
    @Bean
    fun filterChain(security: HttpSecurity): SecurityFilterChain {
        return security
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .exceptionHandling().authenticationEntryPoint { req, res, ex ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }.and()
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(HttpMethod.POST, "/restaurant/login", "/restaurant").permitAll()
                    .requestMatchers(HttpMethod.POST, "/costumers/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/error/**").permitAll()
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .anyRequest().authenticated()
            }
            .headers {  it.frameOptions().disable() }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun corsFilter() =
        CorsConfiguration().apply {
            addAllowedHeader("*")
            addAllowedOrigin("*")
            addAllowedMethod("*")
        }.let {
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", it)
            }
        }.let { CorsFilter(it) }


}