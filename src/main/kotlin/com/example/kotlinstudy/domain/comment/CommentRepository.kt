package com.example.kotlinstudy.domain.comment

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.util.Assert
import kotlin.reflect.KProperty1

/**
 * @PackageName : com.example.kotlinstudy.domain.comment
 * @FileName : CommentRepository
 * @Author : noglass_gongdae
 * @Date : 2024-09-07
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
interface CommentRepository {
    fun saveComment(comment: Comment): Comment
    fun saveCommentClosure(idDescendant: Long, idAncestor: Long?): Int
    fun findCommentByAncestorComment(idAncestor: Long): List<Comment>
}

@Repository
class CommentRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory,
        private val entityManager: EntityManager
): CommentRepository {

    override fun saveComment(comment: Comment): Comment {
        Assert.notNull(comment, "Entity must not be null")
        return if(comment.id == 0L){
            entityManager.persist(comment)
            comment
        } else {
            entityManager.merge(comment)
        }
    }


    override fun saveCommentClosure(idDescendant: Long, idAncestor: Long?): Int {
        // closure 테이블 전략

        var executeCount = 0

        val sql = """
            INSERT INTO comment_closure
            (id_ancestor, id_descendant, depth, updated_at, created_at)
            VALUES
            ($idAncestor, $idDescendant, 0, now(), now())
        """.trimIndent()

        executeCount += entityManager.createNativeQuery(sql).executeUpdate()

        if(idAncestor != null){ //대댓글 이상이라면
            executeCount += entityManager.createNativeQuery("""
                INSERT INTO comment_closure  (id_ancestor, id_descendant, depth, updated_at, created_at) 
                SELECT cc.id_ancestor, c.id_descendant, cc.depth + c.depth + 1, c.updated_at, c.created_at 
                FROM comment_closure as cc, comment_closure as c
                WHERE cc.id_descendant = $idAncestor and c.id_ancestor = $idDescendant
            """.trimIndent()).executeUpdate()
        }
        return executeCount
    }

    override fun findCommentByAncestorComment(idAncestor: Long): List<Comment> { // 계층형 댓글을 찾아오는 JDSL

        return queryFactory.listQuery<Comment> {
            select(entity(Comment::class))
            from(entity(Comment::class))
            join(
                    entity(CommentClosure::class),
                    on(entity(Comment::class).equal(column(CommentClosure::idDescendant)))
            )
            where(
                    nestedCol(col(CommentClosure::idAncestor as KProperty1<CommentClosure, Comment>), Comment::id).equal(idAncestor)
            )
        }

    }


}