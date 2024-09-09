package com.example.kotlinstudy.config.cache

/**
 * @PackageName : com.example.kotlinstudy.config.cache
 * @FileName : CacheType
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

enum class CacheType(
        val cacheName: String,
        val expiredAfterWrite: Long,
        val maximumSize: Long,
        val cacheKey:String,
) {

//    POST_NAME("postName", 100, 10_000, "postTitleKey");
//    // enum은 이미 정해진 값 같지만, 실제로는 생성자를 통해 속성 값을 가질 수 있음
//
//    companion object { // 정적 멤버 필드로 동작
//        const val postName = "postName"
//    }
    POST_NAME(Constant.postName, 100, 10_000, "postTitleKey");

    object Constant { // 독립적인 Singleton 객체를 정의 및 객체명으로 직접 접근
        const val postName = "postName"
    }

}