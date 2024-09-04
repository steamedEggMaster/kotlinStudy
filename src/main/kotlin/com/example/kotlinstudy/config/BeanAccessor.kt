package com.example.kotlinstudy.config

import com.example.kotlinstudy.util.value.Cat
import com.example.kotlinstudy.util.value.Dog
import mu.KotlinLogging
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

/**
 * @PackageName : com.example.kotlinstudy.util
 * @FileName : BeanAccessor
 * @Author : noglass_gongdae
 * @Date : 2024-09-04
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration(proxyBeanMethods = false)
// @Bean에 등록된 test메서드를 보면
// 총 2번의 Dog 클래스를 불러 인스턴스를 반환하여 Bean으로 만듬
// true(default 값)일땐, Proxy가 이케이케 해서 알아서 Singleton 으로 Bean을 만들어줌
// false로 하면 Proxy가 없어서, 불러지는 클래스 만큼 Bean이 만들어짐
// true로 할시, Proxy에 의해 성능상 overhead 발생 가능
class BeanAccessor(

) : ApplicationContextAware {

    private val log = KotlinLogging.logger {  }

    init {
        log.info { "this BeanAccessor => $this" }
    }

    @Bean
    fun test(): Dog {
        return Dog()
    }

    @Bean
    fun test2(): Cat {
        return Cat(Dog())
    }



    override fun setApplicationContext(applicationContext: ApplicationContext) {
        BeanAccessor.applicationContext = applicationContext
    }

    companion object{
        private lateinit var applicationContext: ApplicationContext
        fun <T : Any> getBean(type:KClass<T>): T {
            return applicationContext.getBean(type.java)
        }

        fun <T : Any> getBean(name:String, type:KClass<T>): T {
            return applicationContext.getBean(name, type.java)
        }
    }
}