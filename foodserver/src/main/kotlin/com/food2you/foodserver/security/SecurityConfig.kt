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
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(HttpMethod.POST).permitAll()
                    .requestMatchers(HttpMethod.DELETE).permitAll()
                    .requestMatchers(HttpMethod.PUT).permitAll()
                    .requestMatchers(HttpMethod.GET, "/error/**").permitAll()
                    .requestMatchers("/error/**").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/costumers/**").permitAll()
//                    .requestMatchers(HttpMethod.PUT, "/costumers/**").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/restaurant/**").permitAll()
//                    .requestMatchers(HttpMethod.PUT, "/restaurant/**").permitAll()
                    .anyRequest().authenticated()
            }
            .headers {  it.frameOptions().disable() }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.addAllowedHeader("*")
        config.addAllowedOrigin("*")
        config.addAllowedMethod("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

}