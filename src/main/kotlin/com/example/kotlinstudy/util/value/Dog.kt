package com.example.kotlinstudy.util.value

import mu.KotlinLogging

/**
 * @PackageName : com.example.kotlinstudy.util.value
 * @FileName : Dog
 * @Author : noglass_gongdae
 * @Date : 2024-09-04
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
class Dog(

) {

    private val log = KotlinLogging.logger {  }

    init {
        log.info { "this BeanAccessor => $this" }
    }

    val age = 10
}

class Cat(
       val dog: Dog
){

}