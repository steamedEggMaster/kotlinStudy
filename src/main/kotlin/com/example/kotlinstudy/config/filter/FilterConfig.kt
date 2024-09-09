package com.example.kotlinstudy.config.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

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
    * 1. 비동기 처리 - 코루틴 vs @async completureFuture
    * 2. 파일 핸들링 - s3 완료
    * 3. sse event + web socket을 활용한 실시간 챗봇
    * 4. aws 배포
    * 5. actuator + admin-server를 통한 간단한 모니터링
    * 6. code deploy + github action을 통한 cicd or docker-compose
    * 7. 스프링 시큐리티 + JWT 인증처리 - 완료
    * 8. junit + mockito 테스트 환경 설정
    * 9. restdoc 통한 API 문서 자동화 + controller 테스트 + swagger 연동
    * 10. gradle 멀티모듈을 통해, domain을 공유하는 Batch 서버 작성
    * 11. 인메모리 concurrentHashMap을 통한 cache 적용
    * 12. 계층형 테이블 전략 - closure
    * 13. 스프링 클라우드 모듈들을 활용해서 간단하게 MSA 환경 구축
    * 14. 게시글 예약 발생 시간, 공개/비공개 처리, 스케줄러 + entityListener +
    * 15. attributeConverter 활용
    * 16. 검색어 자동완성 API
    * 17. nginx를 이용한 무중단 배포
    * 18. 핵사고널 아키텍쳐로 변환
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
    fun mdcLoggingFilter(): FilterRegistrationBean<MDCLoggingFilter> {
        val bean = FilterRegistrationBean(MDCLoggingFilter())

        bean.addUrlPatterns("/*") // * 하나임!
        bean.order = Ordered.HIGHEST_PRECEDENCE

        return bean
    }
}