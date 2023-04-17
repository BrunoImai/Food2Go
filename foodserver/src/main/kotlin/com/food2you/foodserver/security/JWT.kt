package com.food2you.foodserver.security

import com.food2you.foodserver.costumer.Costumer
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.weaver.NameMangler.PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@Component
data class JWT (

    private val prefix: String = "Bearer",
    private val issuer: String = "PUCPR Server",
    private val secret: String = "8y/B?E(H+MbQeThWmZq3t6w9zC&F)J@",
    private val costumer: String = "costumer",
    private val days: Int = 2

) {
    private fun toDate(date: LocalDate): Date {
        return Date.from(date.atStartOfDay(ZoneOffset.UTC).toInstant())
    }

    fun createToken(costumer: Costumer): String {
        val now = LocalDate.now()
        val costumerToken = CostumerToken(
            costumer.id!!,
            costumer.email,
            costumer.name,
            costumer.roles
        )

        return Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .serializeToJsonWith(JacksonSerializer())
            .setIssuedAt(toDate(now))
            .setExpiration(toDate(now.plusDays(days.toLong())))
            .setIssuer(issuer)
            .setSubject(costumer.id.toString())
            .addClaims(mutableMapOf("customerId" to costumer.id, "customerToken" to costumerToken))
            .compact()
    }

    fun extract(req: HttpServletRequest): Authentication? {
        val header = req.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || !header.startsWith(PREFIX)) return null

        val token = header.replace(PREFIX, "").trim()

        val claims = Jwts.parserBuilder()
            .setSigningKey(secret.toByteArray())
            .deserializeJsonWith(JacksonDeserializer(mapOf(costumer to CostumerToken::class.java)))
            .build()
            .parseClaimsJws(token)
            .body

        if (issuer != claims.issuer) return null

        val costumer = claims[costumer, CostumerToken::class.java] ?: return null
        val authorities = costumer.roles.map { SimpleGrantedAuthority("ROLE_$it") }
        return UsernamePasswordAuthenticationToken(costumer, costumer.id, authorities)
    }
}



