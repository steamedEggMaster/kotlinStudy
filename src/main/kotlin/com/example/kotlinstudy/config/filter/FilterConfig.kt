package com.example.kotlinstudy.config.filter

import jakarta.servlet.FilterRegistration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @PackageName : com.example.kotlinstudy.config.filter
 * @FileName : FilterConfig
 * @Author : noglass_gongdae
 * @Date : 2024-09-01
 * @Blog : https://blog.naver.com/noglass_gongdae
 * @GitHub :
 */

@Configuration
class FilterConfig {

    /*
    * 이런 필터랑 인터셉터를 이용해서 만든 강력한 인증처리 관련 프레임워크 존재
    * 스프링 시큐리티!!
    *
    * 1. 비동기 처리
    * 2. 파일 핸들링
    * 3. sse event + web socket을 활용한 실시간 챗봇
    * 4. aws 배포
    * 5. actuator + admin-server를 통한 간단한 모니터링
    * 6. code deploy + github action을 통한 cicd
    * 7. 스프링 시큐리티 + JWT 인증처리
    * 8. junit + mock 테스트 환경 설정
    * 9. restdoc 통한 API 문서 자동화
    * 10. gradle 멀티모듈을 통해, domain을 공유하는 Batch 서버 작성
    * 11. 인메모리 concurrentHashMap을 통한 cache 적용
    * 12. 계층형 테이블 전략
    * 13. 스프링 클라우드 모듈들을 활용해서 간단하게 MSA 환경 구축
    * 14. Docker 연동하여 배포
    *
    * ===== front =====
    * 1. react - typescript 환경 셋팅
    * 2. recail + zustand를 통한 상태관리
    * 3, pm2를 활용한 배포, 모니터링
    * 4. 정적 페이지 서버로써 s3에 배포
    * 5. next.js?? (미정) 를 활용한 서버사이트렌더링 체험 + SED
    * 6. antd를 활용한 ui 컴포넌트 활용
    * 7. 반응형 스타일링
    * 8. webpack 최적화 + userailback을 활용한 랜더링 최적화
    * */


    @Bean
    fun registMyAuthenticationFilter(): FilterRegistrationBean<MyAuthenticationFilter> {
        val bean = FilterRegistrationBean(MyAuthenticationFilter())

        bean.addUrlPatterns("/api/*") // * 하나임!
        bean.order = 0

        return bean
    }
}