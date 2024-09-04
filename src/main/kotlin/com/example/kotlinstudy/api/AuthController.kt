package com.example.kotlinstudy.api

import com.example.kotlinstudy.domain.member.LoginDto
import com.example.kotlinstudy.service.AuthService
import com.example.kotlinstudy.service.MemberService
import com.example.kotlinstudy.util.value.CmResDto
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : AuthController
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RequestMapping("/auth")
@RestController
class AuthController(
        private val authService: AuthService
) {

    val log = KotlinLogging.logger {  }

    @GetMapping("/login")
    fun login(session: HttpSession){
        session.setAttribute("principal", "pass")
    }

    @PostMapping("/member")
    fun joinApp(@Valid @RequestBody dto: LoginDto): CmResDto<*> {
        return CmResDto(HttpStatus.OK, "회원가입r", authService.saveMember(dto))

    }
}