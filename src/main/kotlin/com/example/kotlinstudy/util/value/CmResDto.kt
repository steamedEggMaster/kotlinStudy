package com.example.kotlinstudy.util.value

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
        var resultMsg:String,
        val data:T
) {
    fun reflectVersion(apiVersion: String){
        this.resultMsg = "version: $apiVersion / ${this.resultMsg}"
    }
}