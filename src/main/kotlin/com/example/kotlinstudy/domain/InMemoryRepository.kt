package com.example.kotlinstudy.domain

import java.util.*
import kotlin.collections.ArrayList

/**
 * @PackageName : com.example.kotlinstudy.domain
 * @FileName : InMemoryRepository
 * @Author : noglass_gongdae
 * @Date : 2024-09-04
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
interface InMemoryRepository {

    fun clear()
    fun remove(key: String) : Any?
    fun findAll(): MutableList<Any>
    fun findByKey(key: String) : Any
    fun save(key: String, value: Any)

}