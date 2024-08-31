package com.example.kotlinstudy.domain.post

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRes

/**
 * @PackageName : com.example.kotlinstudy.domain.post
 * @FileName : PostDto
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

data class PostSaveReq(
        val title:String,
        val content:String,
        val memberId: Long
)

fun PostSaveReq.toEntity() : Post{
    return Post(
            title = this.title,
            content = this.content,
            member = Member.createFakeMember(this.memberId)
    )
}

data class PostRes(
        val id:Long,
        val title:String,
        val content:String,
        val member: MemberRes
)