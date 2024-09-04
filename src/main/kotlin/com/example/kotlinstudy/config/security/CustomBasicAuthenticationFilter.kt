package com.example.kotlinstudy.config.security

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.example.kotlinstudy.domain.member.MemberRepository
import com.example.kotlinstudy.util.CookieProvider
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.RuntimeException

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : CustomBasicAuthenticationFilter
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

class CustomBasicAuthenticationFilter(
        private val objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

    val log = KotlinLogging.logger { }

    private val jwtManager = JwtManager()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val path = request.servletPath

        // /login 경로는 필터를 적용하지 않음
        if (path == "/login" || path == "/auth/member") {
            chain.doFilter(request, response)
            return
        }


        log.info { "권한이나 인증이 필요한 요청이 들어왔습니다." }

        val accessToken = request.getHeader(jwtManager.authorizationHeader)?.substring(7)
        if (accessToken == null) {
            log.info { "토큰이 없습니다." }
            chain.doFilter(request, response)
            return
        }

        log.info { "accessToken : $accessToken" }

        val accessTokenResult: TokenValidResult = jwtManager.validAccessToken(accessToken)
        if (accessTokenResult is TokenValidResult.Failure) {
            handleTokenException(accessTokenResult) {
                if (accessTokenResult.exception is TokenExpiredException) {
                    log.info { "getClass ==> ${accessTokenResult.exception.javaClass}" }

                    val refreshToken = CookieProvider.getCookie(request, CookieProvider.CookieName.REFRESH_COOKIE).orElseThrow()
                    val refreshTokenResult = jwtManager.validRefreshToken(refreshToken)

                    if (refreshTokenResult is TokenValidResult.Failure) {
                        throw RuntimeException("invalid refreshToken")
                    }

                    log.info { "refreshToken을 사용하여 인증합시다" }

                    val principalString = jwtManager.getPrincipalByRefreshToken(refreshToken)
                    val details = objectMapper.readValue(principalString, PrincipalDetails::class.java)
                    reissueAccessToken(details, response)
                    setAuthentication(details, chain, request, response)
                }
            }
            return
        }

        val principalJsonData = jwtManager.getPrincipalByAccessToken(accessToken)
        val principalDetails = objectMapper.readValue(principalJsonData, PrincipalDetails::class.java)
        setAuthentication(principalDetails, chain, request, response)

        //DB로 호출 함
        //val member = memberRepository.findMemberByEmail(details.member.email)
        //val principalDetails = PrincipalDetails(member) // 인증 객체
//        val authentication: Authentication = UsernamePasswordAuthenticationToken(
//                principalDetails,
//                principalDetails.password,
//                principalDetails.authorities)
//        SecurityContextHolder.getContext().authentication = authentication // holder에 authentication이 넣어지면 인증이 완료되었다는 것
//        chain.doFilter(request, response)
    }

    private fun setAuthentication(details: PrincipalDetails, chain: FilterChain, request: HttpServletRequest, response: HttpServletResponse) {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(
                details,
                details.password,
                details.authorities)

        SecurityContextHolder.getContext().authentication = authentication

        chain.doFilter(request, response)
    }

    private fun reissueAccessToken(details: PrincipalDetails?, response: HttpServletResponse) {
        val recreatedAccessToken = jwtManager.generateAccessToken(objectMapper.writeValueAsString(details))
        response.addHeader(jwtManager.authorizationHeader, jwtManager.jwtHeader + recreatedAccessToken)
    }

    private fun handleTokenException(tokenValidResult: TokenValidResult.Failure, func: () -> Unit) {
        when (tokenValidResult.exception) {
            is TokenExpiredException -> func()
            else -> {
                log.info { "여기 타는지 체크" }
                log.error(tokenValidResult.exception.stackTraceToString())
                throw tokenValidResult.exception
            }
        }
    }


//    private fun reissueAccessToken(exception: JWTVerificationException, request: HttpServletRequest?) {
//        if (exception is TokenExpiredException) {
//            val principalDetails = ObjectMapper().readValue(principalSpring, PrincipalDetails::class.java)
//
//            val authentication: Authentication = UsernamePasswordAuthenticationToken(principalDetails, principalDetails.password, principalDetails.authorities)
//
//            SecurityContextHolder.getContext().authentication = authentication
//        }
//    }
}