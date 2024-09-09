package com.example.kotlinstudy.setup

import org.mockito.Mockito

/**
 * @PackageName : com.example.kotlinstudy.setup
 * @FileName : MokitoHelper
 * @Author : noglass_gongdae
 * @Date : 2024-09-08
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
object MockitoHelper {

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}