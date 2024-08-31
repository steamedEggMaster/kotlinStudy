package com.example.kotlinstudy.api

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @PackageName : com.example.kotlinstudy.api
 * @FileName : MemberController
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@RestController
class MemberController(
        private var memberService: MemberService
) {

    @GetMapping("/members")
    fun findAll(): MutableList<Member> {
        return memberService.findAll()
    }
}