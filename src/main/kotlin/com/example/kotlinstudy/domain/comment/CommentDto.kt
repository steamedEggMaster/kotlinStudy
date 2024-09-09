package com.example.kotlinstudy.domain.comment

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.MemberRes
import com.example.kotlinstudy.domain.post.Post
import com.example.kotlinstudy.util.dto.BaseDto

/**
 * @PackageName : com.example.kotlinstudy.domain.comment
 * @FileName : CommentDto
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

data class CommentSaveReq(
        val memberId:Long,
        val content:String,
        val postId:Long,
        val idAncestor:Long?
) {
    fun  toEntity(post: Post):Comment {
        return Comment(
                content = this.content,
                post = post,
                member = Member.createFakeMember(this.memberId)
        )
    }
}

data class CommentRes(
        val content: String,
        val member: MemberRes
) :BaseDto() {

}