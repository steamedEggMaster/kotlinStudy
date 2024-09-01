package com.example.kotlinstudy.service

import com.example.kotlinstudy.domain.member.*
import com.example.kotlinstudy.exception.MemberNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    fun findAll(pageable: Pageable): Page<MemberRes> = memberRepository.findMembers(pageable).map { it.toDto() }

    //    @Transactional(readOnly = true)
    //    fun findAll() : MutableList<Member> {
    //        return memberRepository.findAll()
    //    }

    @Transactional
    fun saveMember(dto:LoginDto): MemberRes {
        return memberRepository.save(dto.toEntity()).toDto()
    }

    @Transactional
    fun deleteMember(id : Long){
        return memberRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findMemberById(id:Long): MemberRes {
        return memberRepository.findById(id)
                .orElseThrow {
                    throw MemberNotFoundException(id)
                }.toDto()
    }
}