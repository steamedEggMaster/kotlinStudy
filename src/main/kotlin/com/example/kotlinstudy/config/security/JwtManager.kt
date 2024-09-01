package com.example.kotlinstudy.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.Date

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : JwtManager
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Component
class JwtManager {

    private val log = KotlinLogging.logger {  }
    private val secretKey:String = "asfagdfbs"
    private val claimEmail = "email"
    private val claimPassword = "password"
    private val expireTime = 1000 * 60 * 60
    val authorizationHeader = "Authorization"
    val jwtHeader = "Bearer "

    fun generateAccessToken(principal:PrincipalDetails): String? {

        return JWT.create()
                .withSubject(principal.username)
                // subject : 토큰의 주체 나타냄
                .withExpiresAt(Date(System.nanoTime() + expireTime))
                .withClaim(claimEmail, principal.username)
                // claimEmail이라는 클레임에 해당 username값 할당
                // claim? : JWT 토큰의 Payload에 들어가는 값
                .withClaim(claimPassword, principal.password)
                .sign(Algorithm.HMAC512(secretKey))

    }

    fun getMemberEmail(token:String): String? {
        return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
                .getClaim(claimEmail).asString()
    }
}
