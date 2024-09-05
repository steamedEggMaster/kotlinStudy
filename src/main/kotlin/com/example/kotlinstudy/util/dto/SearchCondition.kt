package com.example.kotlinstudy.util.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import mu.KotlinLogging

/**
 * @PackageName : com.example.kotlinstudy.util.dto
 * @FileName : SearchCondition
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

class SearchCondition(
        //val searchType: SearchType?,
        @JsonProperty("searchType") val searchType: SearchType?,
        @JsonProperty("keyword") val keyword: String?
) {


}

enum class SearchType {

    EMAIL, TITLE, CONTENT;

    private val log = KotlinLogging.logger {  }


    @JsonCreator // 기본 생성자가 없거나, 파라미터가 다른 경우 많이 사용함
    // enum 클래스를 만들기 위해 사용
    // 해당 어노테이션을 붙이지 않으면, SearchCondition의 객체를 가져와서 만들지 못함
    fun from(s: String?): SearchType {
//        return entries.firstOrNull { it.name == s?.uppercase() }
        log.info { "s : $s" }

        val searchType = SearchType.valueOf(s?.uppercase().toString())

        log.info { "searchType : $searchType" }
        return searchType
    }


}