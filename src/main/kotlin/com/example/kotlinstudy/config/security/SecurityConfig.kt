package com.example.kotlinstudy.config.security

import com.example.kotlinstudy.domain.member.MemberRepository
import com.example.kotlinstudy.util.func.responseData
import com.example.kotlinstudy.util.value.CmResDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : SecurityConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
@EnableWebSecurity//(debug = true) // 거쳐간 필터들 알 수 있음
//@EnableMethodSecurity(securedEnabled = true) // EnableGlobalMethodSecurity가 deprecated 됨
class SecurityConfig(
        private val authenticationConfiguration: AuthenticationConfiguration,
        private val objectMapper: ObjectMapper,
        private val memberRepository: MemberRepository
) {
    private val log = KotlinLogging.logger { }

    //@Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers("/ignore1", "/ignore2") // 모든것을 허용할 시 "/**" *두개
        }
    }

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
                .csrf { auth: CsrfConfigurer<HttpSecurity> -> auth.disable() }
        http
                .formLogin { auth -> auth.disable() }
        http
                .headers { headers -> headers.frameOptions(Customizer { frameOptionsConfig -> frameOptionsConfig.disable() }) }
        http
                .httpBasic { auth -> auth.disable() }
        http
                .sessionManagement { session: SessionManagementConfigurer<HttpSecurity> ->
                    session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
        http
                .cors { corsCustomizer: CorsConfigurer<HttpSecurity> ->
                    corsCustomizer.configurationSource(corsConfig())
                }
        http
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java).exceptionHandling(Customizer { auth -> auth.accessDeniedHandler(CustomAccessDeniedHandler()) })
        http
                .authorizeHttpRequests(Customizer { auth ->
                    auth
                            //.requestMatchers("/**").hasRole("USER")
                            .requestMatchers("/v1/posts").hasAnyRole("USER", "ADMIN")

                })
        http
                .logout { auth:LogoutConfigurer<HttpSecurity> -> // Customizer 을 붙이면 안됨
                    auth
                            .logoutUrl("/logout")
                            .logoutSuccessHandler(CustomLogoutSuccessHandler(objectMapper))
                }

        return http.build()
    }

    //로그아웃 성공 핸들러 커스텀
    class CustomLogoutSuccessHandler(
            private val objectMapper: ObjectMapper
    ) : LogoutSuccessHandler {
        private val log = KotlinLogging.logger {  }
        override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse, authentication: Authentication?) {
            log.info { "logout success" }
            val context = SecurityContextHolder.getContext()
            context.authentication = null
            SecurityContextHolder.clearContext()

            val cmResDto = CmResDto(HttpStatus.OK, "logout success", null)

            responseData(response, objectMapper.writeValueAsString(cmResDto))
        }
    }

    // 필터 통과 실패 시 핸들링 할 클래스
    class CustomAccessDeniedHandler: AccessDeniedHandler {
        private val log = KotlinLogging.logger { }

        override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, accessDeniedException: AccessDeniedException?) {
            log.info { "access denied!!" }
            response?.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    @Bean
    fun authenticationFilter(): CustomBasicAuthenticationFilter {
        return CustomBasicAuthenticationFilter(authenticationManager = authenticationManager(), memberRepository = memberRepository, objectMapper = objectMapper)
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun loginFilter(): UsernamePasswordAuthenticationFilter {

        val authenticationFilter = CustomUserNameAuthenticationFilter(objectMapper)
        authenticationFilter.setAuthenticationManager(authenticationManager())
        authenticationFilter.setFilterProcessesUrl("/login")
//        authenticationFilter.setAuthenticationFailureHandler(CustomAuthenticationFailureHandler())
//        authenticationFilter.setAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler())

        return authenticationFilter
    }

//    class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
//        private val log = KotlinLogging.logger {  }
//
//        override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
//            log.info { "로그인 실패입니다." }
//            response?.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 실패입니다.")
//        }
//
//    }
//    class CustomAuthenticationSuccessHandler : AuthenticationSuccessHandler {
//        private val log = KotlinLogging.logger {  }
//
//        override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
//            log.info { "로그인 성공입니다." }
//            response?.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 성공입니다.")
//        }
//    }

    @Bean
    fun corsConfig(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        config.addExposedHeader("authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

}