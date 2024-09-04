package com.example.kotlinstudy.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : JwtManager
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Component
class JwtManager(
        accessTokenExpireMinute:Long = 30, //1분
        refreshTokenExpireDay:Long = 7
) {

    private val log = KotlinLogging.logger {  }
    private val accessSecretKey:String = "myAccessSecretKey"
    private val refreshSecretKey:String = "myRefreshSecretKey"

    private val accessTokenExpireSecond:Long = accessTokenExpireMinute
    val refreshTokenExpireDay:Long = refreshTokenExpireDay

    val claimPrincipal = "principal"

    val authorizationHeader = "Authorization"
    val jwtHeader = "Bearer "

    private val jwtSubject = "my-token"

    fun generateRefreshToken(principal: String): String {
        val expireDate = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(refreshTokenExpireDay))

        log.info { "refreshToken ExpireDate => $expireDate" }

        return doGenerateToken(expireDate, principal, refreshSecretKey)
    }

    fun generateAccessToken(principal:String): String {

        val expireDate = Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(accessTokenExpireSecond))
        log.info { "accessToken ExpireDate => $expireDate" }

        return doGenerateToken(expireDate, principal, accessSecretKey)
    }

    private fun doGenerateToken(expireDate: Date, principal: String, secretKey: String): String = JWT.create()
            .withSubject(jwtSubject)
            // subject : 토큰의 주체 나타냄
            .withExpiresAt(expireDate)
            .withClaim(claimPrincipal, principal)
            .sign(Algorithm.HMAC512(secretKey))

    fun getPrincipalByAccessToken(accessToken: String): String {
        val decodeJWT = getDecodeJwt(secretKey = accessSecretKey, token = accessToken)
        return decodeJWT.getClaim(claimPrincipal).asString()
    }
    fun getPrincipalByRefreshToken(refreshToken: String): String {
        val decodeJWT = getDecodeJwt(secretKey = refreshSecretKey, token = refreshToken)
        return decodeJWT.getClaim(claimPrincipal).asString()
    }

    fun getDecodeJwt(token: String, secretKey:String): DecodedJWT {
        val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        return decodedJWT
    }

    fun validAccessToken(token: String): TokenValidResult {
        return validatedJwt(token, accessSecretKey)
    }

    fun validRefreshToken(token: String): TokenValidResult {
        return validatedJwt(token, refreshSecretKey)
    }

    private fun validatedJwt(token:String, secretKey: String): TokenValidResult { // return값이 true | JWTVerificationException
        return try {
            getDecodeJwt(token, secretKey)
            TokenValidResult.Success()
        } catch (exception: JWTVerificationException) {
            //log.error { "error => ${exception.stackTraceToString()}" }
            TokenValidResult.Failure(exception)
        }
    }
}

/*
* 코틀린으로 UnionType을 흉내내자 */
//
//
sealed class TokenValidResult(){
    class Success(val successValue:Boolean = true) : TokenValidResult()
    class Failure(val exception:JWTVerificationException) : TokenValidResult()
}

//class TokenValidResult(
//        val isValid:Boolean,
//        val exception: JWTVerificationException ?= null,
//        val decodedJWT: DecodedJWT ?= null
//
//){
//}
