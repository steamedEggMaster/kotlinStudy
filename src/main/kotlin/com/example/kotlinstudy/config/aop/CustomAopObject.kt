package com.example.kotlinstudy.config.aop

import mu.KotlinLogging

/**
 * @PackageName : com.example.kotlinstudy.config.aop
 * @FileName : CustomAopObject
 * @Author : noglass_gongdae
 * @Date : 2024-09-03
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */
object CustomAopObject {

    private val log = KotlinLogging.logger {  }

    /*
    * 1급 시민의 조건(코틀린에서 함수도 일급 시민이다)
    * 1. 인자로 넘겨줄 수 있다.
    * 2. returnType으로 정의 할 수 있다.
    * 3. 값에 할당 가능하다.
    *
    * 고차 함수를 통해서, AOP를 구현해보자
    *
    * */

    fun highOrderFunc(func:()->Unit){
        log.info { "before" }
        func()
        log.info { "after" }
    }



}
fun main(){
    doSomething()
}
            // 3번 조건
fun doSomething() = CustomAopObject.highOrderFunc { (println("do something")) }
                                                     // 1번 조건 + 마지막 인자 -> { } 로 빼서 넘기기 가능