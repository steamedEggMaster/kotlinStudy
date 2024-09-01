package com.example.kotlinstudy.domain.post

import com.example.kotlinstudy.domain.member.Member
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

/**
 * @PackageName : com.example.kotlinstudy.domain.post
 * @FileName : PostRepository
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
interface PostRepository : JpaRepository<Post, Long>, PostCustomRepository {
}

interface PostCustomRepository{

    fun findPosts(pageable: Pageable): Page<Post>
}

class PostCustomRepositoryImpl(
        private val queryFactory: SpringDataQueryFactory
):PostCustomRepository {
    override fun findPosts(pageable: Pageable): Page<Post> {
        val results = queryFactory.listQuery<Post> {
            select(entity(Post::class))
            from(entity(Post::class))
            fetch(Post::member) // 3개였던 쿼리문을 Post의 Member를 가져올때, inner join(기본값)을 통해 한번에 가져옴 -> 쿼리문 줄어듬
            // fetch(Post::member, JoinType.LEFT / RIGHT) 쓰면 됨
            limit(pageable.pageSize)
            offset(pageable.offset.toInt())
            orderBy(ExpressionOrderSpec(column(Post::id), false))
        }

        val countQuery = queryFactory.listQuery<Post> {
            select(entity(Post::class))
            from(entity(Post::class))
        }

        return PageableExecutionUtils.getPage(results, pageable){
            countQuery.size.toLong()
        }
    }
}