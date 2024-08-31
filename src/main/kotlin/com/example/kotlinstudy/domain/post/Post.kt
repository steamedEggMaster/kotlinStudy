package com.example.kotlinstudy.domain.post

import com.example.kotlinstudy.domain.AuditingEntity
import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.member.toDto
import jakarta.persistence.*
import jdk.jfr.Enabled

/**
 * @PackageName : com.example.kotlinstudy.domain
 * @FileName : Post
 * @Author : noglass_gongdae
 * @Date : 2024-08-30
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Entity
@Table(name = "post")
class Post(
        title:String,
        content:String,
        member:Member
) : AuditingEntity() {

        @Column(name = "title", nullable = false)
        var title:String = title
                private set

        @Column(name = "content")
        var content:String = content // var == type
                private set

        @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
        var member:Member = member
                private set

        override fun toString(): String {
                return "Post(id = $id, title='$title', content='$content', member=$member)"
        }


}

fun Post.toDto() : PostRes{
        return PostRes(
                id = this.id!!,
                title = this.title,
                content = this.content,
                member = this.member.toDto()
        )
}

