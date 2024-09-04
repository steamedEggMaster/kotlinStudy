package com.example.kotlinstudy.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.http.ResponseCookie
import java.util.*

/**
 * @PackageName : com.example.kotlinstudy.util
 * @FileName : CookieProvider
 * @Author : noglass_gongdae
 * @Date : 2024-09-02
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
object CookieProvider { //object 파일은 싱글톤이면서 바로 static하게 바로 메모리에 올라와 쓸 수 있게함

    private val log = KotlinLogging.logger {  }

    fun createNullCookie(cookieName:String){
        TODO()
    }

    fun createCookie(cookieName: CookieName, value:String, maxAge:Long): ResponseCookie { // Not Servlet Cookie!!
        return ResponseCookie.from(cookieName.name, value)
                .httpOnly(true) // cross-site script 공격 (클라이언트 단에서 Javascript를 변조하여 공격)을 방어 가능해짐
                .secure(false) // http 허용 옵션
                .path("/") // 모든 경로 허용
                .maxAge(maxAge) // second 단위
                //.domain()
                .build()
        // ResponseCookie 란? Spring Framework에서 제공하는 쿠키
        // servlet Cookie보다 좋은 점? 1. SameSite 속성 지원
        //                              => CSRF 공격을 방어하기 좋음
        // 앞으론 이 방법을 사용하자!!
    }

    fun getCookie(request: HttpServletRequest, cookieName: CookieName): Optional<String> {
        val cookieValue = request.cookies.filter { cookie: Cookie ->
            cookie.name == cookieName.name
        }.map { cookie -> cookie.value }
                .firstOrNull()

        log.info { "cookieValue==> $cookieValue" }

        return Optional.ofNullable(cookieValue)
    }

    enum class CookieName(
    ){
        REFRESH_COOKIE
    }
}