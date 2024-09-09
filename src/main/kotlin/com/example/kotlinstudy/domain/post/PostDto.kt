package com.example.kotlinstudy.domain.post

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRes
import com.example.kotlinstudy.util.dto.BaseDto
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

/**
 * @PackageName : com.example.kotlinstudy.domain.post
 * @FileName : PostDto
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

data class PostSaveReq(

        @field:NotNull(message = "require title")
        val title:String?,
        val content:String?,

        @field:NotNull(message = "require memberId")
        val memberId: Long?,
        val reserveAt:LocalDateTime,
        val postType: PostType
) {
    fun toEntity() : Post{
        val post = Post(
                title = this.title ?: "",
                content = this.content ?: "",
                member = Member.createFakeMember(this.memberId!!),
                reserveAt = this.reserveAt,
                postType = this.postType
        )

        if (post.reserveAt != null) post.closedEntity()
        return post
    }
}

data class PostRes(
        val title:String,
        val content:String,
        val member: MemberRes
) : BaseDto()