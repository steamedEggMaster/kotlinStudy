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
                member = this.member.toDto() // FetchType.LAZY 이기 때문에, member를 가져오지 않았다가,
                                             // toDto()에서 member을 찾기 때문에, member를 전부 가져오는 쿼리 발생
        )
}

