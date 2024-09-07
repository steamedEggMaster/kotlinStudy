package com.example.kotlinstudy.config.aop

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * @PackageName : com.example.kotlinstudy.config.app
 * @FileName : LoggerAspect
 * @Author : noglass_gongdae
 * @Date : 2024-08-31
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

/*
* 전통적인 방식의 프록시 기반 스프링 AOP
* */

@Component
@Aspect
class LoggerAspect {

    /*
    * interceptor vs filter vs aspect aop
    * - aspect aop : 로직의 시간 측정, 트랜잭션 관리 등
    * - filter : 공통된 보안 및 인증 및 인가 처리 등, 이미지 데이터 압축 및 문자열 인코딩, 모든 요청 logging
    * - interceptor : Spring으로 부터 분리되어야 하는, 세부적인 보안 설정
    * */

    val log = KotlinLogging.logger { }

    @Pointcut("execution(* com.example.kotlinstudy.web.*Controller.*(..))")
    // api 패키지 밑의 Controller 이름을 붙힌 모든 클래스의 인자가 0개 이상(..)인 메서드에 적용
    private fun controllerCut() = Unit

    @Before("controllerCut()")
    fun controllerLoggerAdvice(joinPoint:JoinPoint){

        val typeName = joinPoint.signature.declaringTypeName
        val methodName = joinPoint.signature.name

        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        log.info { """
            request url : ${request.servletPath}
            type : $typeName
            method : $methodName
        """.trimIndent() }
    }

    @AfterReturning(pointcut = "controllerCut()", returning = "result")
    fun controllerLogAfter(joinPoint: JoinPoint, result : Any){

        val mapper = ObjectMapper()



        log.info { """
            
            ${joinPoint.signature.name} 
            Method return value : ${mapper.writeValueAsString(result)} 
            
            """.trimIndent()}

    }
}