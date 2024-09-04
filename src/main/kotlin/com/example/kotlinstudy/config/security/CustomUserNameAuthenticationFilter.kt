package com.example.kotlinstudy.config.security

import com.example.kotlinstudy.util.CookieProvider
import com.example.kotlinstudy.util.CookieProvider.CookieName
import com.example.kotlinstudy.util.func.responseData
import com.example.kotlinstudy.util.value.CmResDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.concurrent.TimeUnit

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : CustomUserNameAuthenticationFilter
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
class CustomUserNameAuthenticationFilter(
    //private val authenticationManager: AuthenticationManager
        private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {

    private val log = KotlinLogging.logger {  }
    private val jwtManager = JwtManager() // 여기선 그냥 했지만, objectMapper 처럼 주입 받아서 해야함

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        log.info { "login 요청 옴" }

        val username = obtainUsername(request)
        val password = obtainPassword(request)
        // obtain 대신 사용하는 방법인듯
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        log.info { "로그인을 위한 토큰을 만들어 넘깁니다." }
        return authenticationManager.authenticate(authenticationToken)
    }



    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        log.info { "로그인이 완료 되어 JWT 토큰이 발행되어 response 처리됩니다." }
        val principalDetails = authResult.principal as PrincipalDetails

        val accessToken = jwtManager.generateAccessToken(objectMapper.writeValueAsString(principalDetails))
        val refreshToken = jwtManager.generateRefreshToken(objectMapper.writeValueAsString(principalDetails))

        val refreshCookie = CookieProvider.createCookie(CookieName.REFRESH_COOKIE, refreshToken, TimeUnit.DAYS.toSeconds(jwtManager.refreshTokenExpireDay))
        response.addHeader(jwtManager.authorizationHeader, jwtManager.jwtHeader + accessToken)
        //response.addHeader(jwtManager.refreshTokenHeader, jwtManager.jwtHeader + refreshToken)
        // 영상제작자는 accessToken은 Header에 커스텀 헤더로, refreshToken은 쿠키에 감싸서 보냄
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString())
        //response.addHeader(refreshToken) ResponseCookie라서 불가능한 방법

        val jsonResult = objectMapper.writeValueAsString(CmResDto(HttpStatus.OK, "login success", principalDetails.member))
        responseData(response, jsonResult)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, failed: AuthenticationException?) {
        log.info { "로그인 실패" }
        response?.sendError(400)
    }

    // 기본 unsuccessfulAuthentication 가 밑의 코드여서, onAuthenticationFailure를 부를 수가 있었는데, 내가 커스텀하면서 해당 메서드를 부르지 않게됨
    // => 즉 둘 중 하나만 커스텀해서 설정해주면 됨
    //	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
    //			AuthenticationException failed) throws IOException, ServletException {
    //		this.securityContextHolderStrategy.clearContext();
    //		this.logger.trace("Failed to process authentication request", failed);
    //		this.logger.trace("Cleared SecurityContextHolder");
    //		this.logger.trace("Handling authentication failure");
    //		this.rememberMeServices.loginFail(request, response);
    //		this.failureHandler.onAuthenticationFailure(request, response, failed);
    //	}

}