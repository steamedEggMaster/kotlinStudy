package com.example.kotlinstudy.domain.post

import com.example.kotlinstudy.config.jpa.converts.PostTypeConverter
import com.example.kotlinstudy.domain.AuditingEntity
import com.example.kotlinstudy.domain.member.Member
import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

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
        id: Long = 0,
        title:String,
        content:String,
        reserveAt: LocalDateTime,
        postType: PostType,
        member:Member
) : AuditingEntity(id) {

        @Column(name = "title", nullable = false)
        var title:String = title
                private set

        @Column(name = "content")
        var content:String = content
                private set

        @Column(name = "reserve_at")
        var reserveAt:LocalDateTime = reserveAt
                private set

        @Convert(converter = PostTypeConverter::class)
        @Column(name = "post_type")
        var postType:PostType = postType
                private set

        @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
        var member:Member = member
                private set

        override fun toString(): String {
                return "Post(id = $id, title='$title', content='$content', member=$member)"
        }

        fun toDto() : PostRes{
                val dto = PostRes(
                        title = this.title,
                        content = this.content,
                        member = this.member.toDto() // FetchType.LAZY 이기 때문에, member를 가져오지 않았다가,
                        // toDto()에서 member을 찾기 때문에, member를 전부 가져오는 쿼리 발생
                )

                setBaseDtoProperty(dto)
                return dto
        }


        companion object{
        }

}

enum class PostType(
        val info:String
) {
        GOSSIP("잡담"), TECH("기술");

        @JsonCreator
        fun from(s:String): PostType {
                return PostType.valueOf(s.uppercase(Locale.KOREA))
        }

        companion object {
                fun ofCode(dbData:String?): PostType {
                        return Arrays.stream(entries.toTypedArray()).filter {
                                it.name == dbData
                        }.findAny().orElse(GOSSIP)
                }
        }

}