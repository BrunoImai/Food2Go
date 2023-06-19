package com.food2you.foodserver.security

import io.jsonwebtoken.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component

class JwtTokenFilter(private val jwt: JWT) : GenericFilterBean() {
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val auth = jwt.extract(req as HttpServletRequest)
        if (auth != null) SecurityContextHolder.getContext().authentication = auth
        chain.doFilter(req, res)
    }

}