package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @PackageName : com.example.kotlinstudy.service
 * @FileName : MemberService
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Service
class MemberService(
        private val memberRepository: MemberRepository // val == final
) {

    @Transactional(readOnly = true)
    fun findAll() : MutableList<Member> = memberRepository.findAll()

    //    @Transactional(readOnly = true)
    //    fun findAll() : MutableList<Member> {
    //        return memberRepository.findAll()
    //    }


}