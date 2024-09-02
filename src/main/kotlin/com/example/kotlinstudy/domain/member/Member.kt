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
        id: Long = 0,
        email:String,
        password:String,
        role:Role = Role.USER
) : AuditingEntity(id) {

    @Column(name = "email", nullable = false)
    var email:String = email
        private set

    @Column(name = "password", nullable = false)
    var password:String = password
        private set

    @Enumerated(EnumType.STRING)
    var role:Role = role
        private set

    fun toDto(): MemberRes {
        return MemberRes(
                id = this.id!!,
                // !!(Not-null assertion)
                // : kotlin의 null 안정성 기능 중 하나
                // nullable 타입의 변수를 강제로 non-null로 변환
                // => 해당 변수가 null이 아님을 컴파일러가 보장 => 실제로 null이라면 에러 발생
                // => 해당 변수가 null이 아님이라는 확신이 있을 때 사용
                email = this.email,
                password = this.password,
                role = this.role,
                createdAt = this.createdAt,
                updatedAt = this.updatedAt
        )
    }

    override fun toString(): String {
        return "Member(email='$email', password='$password', role=$role, createAt=$createdAt)"
    }


    companion object {
        fun createFakeMember(memberId:Long) : Member{
            val member = Member(id=memberId, "admin@gmail.com", password = "1234")
            member.id = memberId
            return member
        }
    }
}


enum class Role{
    USER, ADMIN
}