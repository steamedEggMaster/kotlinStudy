package com.example.kotlinstudy.domain.member

import org.springframework.data.jpa.repository.JpaRepository

/**
 * @PackageName : com.example.kotlinstudy.domain.member
 * @FileName : MemberRepository
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
interface MemberRepository : JpaRepository<Member, Long> {
}