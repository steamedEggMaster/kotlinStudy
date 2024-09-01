package com.example.kotlinstudy.domain.member

import jakarta.validation.constraints.NotNull

/**
 * @PackageName : com.example.kotlinstudy.domain.member
 * @FileName : MemberDto
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

/**
 * dto <=> Entity 간의 맵핑 시, 크게 2가지 스타일 존재
 * 1. 각 dto, entity에 책임 할당
 * 2. entityMapper 사용 - Mapstruct
 */
// data class : Dto 만들때 Getter Setter 등 다 자동으로 해줌
data class LoginDto(

        @field:NotNull(message = "require email")
        val email:String?,
        val password:String?,
        val role:Role?
)
fun LoginDto.toEntity() : Member {
    return Member(
            email = this.email ?: "",
            password = this.password ?: "",
            role = this.role ?: Role.USER
    )
}
data class MemberRes(
        val id:Long,
        val email:String,
        val password:String,
        val role:Role
)