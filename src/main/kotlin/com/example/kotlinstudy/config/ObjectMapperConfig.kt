package com.example.kotlinstudy.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.serpro69.kfaker.ConfigBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @PackageName : com.example.kotlinstudy.config
 * @FileName : ObjectMapperConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-02
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        mapper.registerModule(
                KotlinModule.Builder()
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.SingletonSupport, false)
                        .build()
        )
        return mapper
    }

}