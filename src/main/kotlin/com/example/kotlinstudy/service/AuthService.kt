package com.example.kotlinstudy.service

import com.example.kotlinstudy.config.security.PrincipalDetails
import com.example.kotlinstudy.domain.member.LoginDto
import com.example.kotlinstudy.domain.member.MemberRepository
import com.example.kotlinstudy.domain.member.MemberRes
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : AuthService
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class AuthService(

        private val memberRepository: MemberRepository

) : UserDetailsService {
    val log = KotlinLogging.logger {  }

    override fun loadUserByUsername(username: String): UserDetails {
        log.info { "로그인 요청으로부터 사용자를 찾습니다." }

        val member = memberRepository.findMemberByEmail(username)

        log.info { "사용자를 찾았습니다." }
        return PrincipalDetails(member)
    }

    @Transactional
    fun saveMember(dto: LoginDto) : MemberRes {
        return memberRepository.save(dto.toEntity()).toDto()
    }
}