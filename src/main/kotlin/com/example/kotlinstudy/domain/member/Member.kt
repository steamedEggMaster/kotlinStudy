package com.example.kotlinstudy.domain.member

import com.example.kotlinstudy.domain.AuditingEntity
import jakarta.persistence.*

/**
 * @PackageName : com.example.kotlinstudy.domain.member
 * @FileName : MemberEntity
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Entity
@Table(name = "member")
class Member(
        email:String,
        password:String,
        role:Role
) : AuditingEntity() {

    @Column(name = "email", nullable = false)
    var email:String = email
        private set

    @Column(name = "password", nullable = false)
    var password:String = password
        private set

    @Enumerated(EnumType.STRING)
    var role:Role = role
        private set

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role)"
    }


}

enum class Role{
    USER, ADMIN
}