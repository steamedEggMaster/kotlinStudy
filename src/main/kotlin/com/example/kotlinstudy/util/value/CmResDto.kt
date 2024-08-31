package com.example.kotlinstudy.util.value

import java.util.TreeMap

/**
 * @PackageName : com.example.kotlinstudy.util.value
 * @FileName : CmResDto
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

data class CmResDto<T>(
        val resultCode: T,
        val resultType:String,
        val data:T
)