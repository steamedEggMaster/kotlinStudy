package com.example.kotlinstudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinStudyApplication

fun main(args: Array<String>) {
    runApplication<KotlinStudyApplication>(*args)
}

/*
* 이제 사이드 컨텐츠를 생각 중인데
*
* back: springboot + kotlin + JPA
* front: react + typescript + zustand
* 배포 : aws ec2 + s3 + codedeploy + github action
*
* */
