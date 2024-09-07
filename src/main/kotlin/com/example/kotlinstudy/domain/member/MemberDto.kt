package com.example.kotlinstudy.domain.member

import com.example.kotlinstudy.config.BeanAccessor
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

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
        val rawPassword:String?,
        val role:Role?
){
    fun toEntity() : Member {
        return Member(
                email = this.email ?: "",
                password = encodeRawPassword(),
                role = this.role ?: Role.USER
        )
    }

    private fun encodeRawPassword():String = BeanAccessor.getBean(PasswordEncoder::class).encode(this.rawPassword)

}
data class MemberRes(
        val id:Long,
        val email:String,
        val password:String,
        val role:Role,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        val createdAt: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        val updatedAt: LocalDateTime
)