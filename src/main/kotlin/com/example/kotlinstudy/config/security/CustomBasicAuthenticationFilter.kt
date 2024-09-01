package com.example.kotlinstudy.config.security

import com.example.kotlinstudy.domain.member.MemberRepository
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
        private val memberRepository : MemberRepository,
    authenticationManager:AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

    val log = KotlinLogging.logger {  }

    private val jwtManager = JwtManager()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val path = request.servletPath

        // /login 경로는 필터를 적용하지 않음
        if (path == "/login") {
            chain.doFilter(request, response)
            return
        }


        log.info { "권한이나 인증이 필요한 요청이 들어왔습니다." }

        val token = request.getHeader(jwtManager.authorizationHeader).substring(7)

        if (token == null) {
            log.info { "토큰이 없습니다." }
            chain.doFilter(request, response)
            return
        }

        log.info { "token : $token" }

        val memberEmail = jwtManager.getMemberEmail(token) ?: throw RuntimeException("memberEmail을 찾을 수 없습니다.")
        val member = memberRepository.findMemberByEmail(memberEmail)
        val principalDetails = PrincipalDetails(member) // 인증 객체
        val authentication:Authentication = UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.authorities)

        SecurityContextHolder.getContext().authentication = authentication // holder에 authentication이 넣어지면 인증이 완료되었다는 것

        log.info { "JWT 검증에 통과하였습니다." }

        chain.doFilter(request, response)
    }
}