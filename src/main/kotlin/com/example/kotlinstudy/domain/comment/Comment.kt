package com.example.kotlinstudy.domain.comment

import com.example.kotlinstudy.domain.AuditingEntity
import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.post.Post
import jakarta.persistence.*

/**
 * @PackageName : com.example.kotlinstudy.domain.comment
 * @FileName : Comment
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Entity
@Table(name = "comment")
class Comment(
        content:String,
        post:Post
) : AuditingEntity() {

    @Column(name = "content", nullable = false)
    var content:String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class)
    var post: Post = post
        private set
}