package com.example.kotlinstudy.domain.comment

import com.example.kotlinstudy.domain.AuditingEntity
import jakarta.persistence.*

/**
 * @PackageName : com.example.kotlinstudy.domain.comment
 * @FileName : CommentClosure
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Entity
@Table(name = "comment_closure")
// 계층형 댓글 설계
class CommentClosure(
        id:Long = 0,
        idAncestor:Comment? = null,
        idDescendant:Comment, // 자기 자신을 포함하기에 무조건 있어야 함
        depth: Int = 0,
) : AuditingEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    // optional = true(기본값) : 외래키가 null이 가능
    // optional = false : 외래키가 null이어선 안됨
    @JoinColumn(name = "id_ancestor", nullable = true)
    var idAncestor = idAncestor
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_descendant", nullable = false)
    var idDescendant = idDescendant
        protected set

    @Column(name = "depth")
    var depth = depth
        protected set

    override fun toString(): String {
        return "CommentClosure(idAncestor=$idAncestor, idDescendant=$idDescendant, depth=$depth)"
    }


}