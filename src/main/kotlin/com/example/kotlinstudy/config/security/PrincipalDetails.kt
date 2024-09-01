package com.example.kotlinstudy.config.security

import com.example.kotlinstudy.domain.member.Member
import mu.KotlinLogging
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @PackageName : com.example.kotlinstudy.config.security
 * @FileName : PrincipalDetails
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
class PrincipalDetails(
        member: Member
        // 생성자에 val or var을 붙여 정의한다면,
        // 해당 프로퍼티는 자동으로 클래스의 멤버변수가 됨
        // 외부에서 해당 변수에 접근 가능해짐
        // 단순히 매개변수로만 선언하여 클래스 내부에서만 사용 가능한 형태로 정의했음
) : UserDetails {

    var member:Member = member
        private set
    // var member:Member 로 매개변수 member를 이용하여 다시 클래스의 멤버변수화 시킴
    // => 외부에서 해당 멤버변수 member에 접근 가능
    // 하지만 private set을 함으로써 외부에선 get만 가능

    private val log = KotlinLogging.logger {  }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        log.info { "Role 검증" }
        val collection:MutableCollection<GrantedAuthority> = ArrayList()
        collection.add(GrantedAuthority { "ROLE_" + member.role })
        return collection
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.email
    }
}