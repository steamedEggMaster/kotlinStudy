package com.example.kotlinstudy.util.func

import com.example.kotlinstudy.domain.member.Member
import com.example.kotlinstudy.domain.post.Post
import com.example.kotlinstudy.util.dto.SearchCondition
import com.example.kotlinstudy.util.dto.SearchType
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import org.slf4j.LoggerFactory

/**
 * @PackageName : com.example.kotlinstudy.util.func
 * @FileName : JdslUtils
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

private val log = LoggerFactory.getLogger(object {}::class.java.`package`.name)

fun <T> SpringDataCriteriaQueryDsl<T>.dynamicQuery(
        searchCondition: SearchCondition?
): PredicateSpec {

    val keyword = searchCondition?.keyword

    log.info("keyword : $keyword")

    return when(searchCondition?.searchType){
        SearchType.CONTENT -> and(keyword?.let { column(Post::content).like("$keyword") })
        SearchType.EMAIL -> and(keyword?.let { column(Member::email).like("$keyword") })
        SearchType.TITLE -> and(keyword?.let { column(Post::title).like("$keyword") })
        else -> { PredicateSpec.empty }
    }
}