package com.example.kotlinstudy.config

import com.example.kotlinstudy.util.dto.SearchType
import mu.KotlinLogging

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.format.FormatterRegistry
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @PackageName : com.example.kotlinstudy.config
 * @FileName : WebMvcConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-05
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class WebMvcConfig(

) : WebMvcConfigurer {

    private val log = KotlinLogging.logger {  }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StringToEnumConverter())
    }

    override fun configurePathMatch(configurer: PathMatchConfigurer) { // AOP

        val apiVersion = "/v1"
        val basePackage = "com.example.kotlinstudy.web"
        configurer.addPathPrefix(apiVersion, HandlerTypePredicate.forBasePackage(basePackage))

    }
}

class StringToEnumConverter : Converter<String, SearchType> {
    override fun convert(source: String): SearchType {

        println("source ==> $source")

        return SearchType.valueOf(source.uppercase())
    }

}