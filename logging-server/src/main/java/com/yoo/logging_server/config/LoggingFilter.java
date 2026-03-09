package com.yoo.logging_server.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 이번 요청을 식별할 고유한 Trace ID 생성 및 MDC에 저장
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("traceId", traceId);
        // 2. Request, Response Body를 여러 번 읽을 수 있도록 캐시 래핑
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            // 3. 실제 컨트롤러 로직 실행 (반드시 래핑된 객체를 넘겨야 함)
            filterChain.doFilter(cachingRequest, cachingResponse);
        } finally {
            // 4. 로직이 끝난 후 캐싱된 내용으로 로그 출력
            long duration = System.currentTimeMillis() - startTime;
            logRequestAndResponse(cachingRequest, cachingResponse, duration);

            // 5. 우리가 읽어버린 응답 Body를 다시 채워넣어 클라이언트에게 전송
            cachingResponse.copyBodyToResponse();

            // 6. 스레드 반환 전 MDC 초기화 (다른 요청에 데이터가 섞이지 않도록)
            MDC.clear();
        } // try - catch
    }

    /**
     * 요청/응답 로그
     *
     * @param request the 캐싱 저장된 Request
     * @param response  the 캐싱 저장된 Request
     * @param duration the 지연 시간
     */
    private void logRequestAndResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) {
        // INFO 레벨: API 호출 요약 (운영 환경에서 볼 내용)
        String method = request.getMethod();
        String requestUri = request.getRequestURI();       // 예: /api/items
        String queryString = request.getQueryString();     // 예: name=사과&amount=10

        // 1. 쿼리 파라미터가 존재하면 URI 뒤에 '?파라미터' 형식으로 결합합니다.
        String fullPath = queryString != null ? requestUri + "?" + queryString : requestUri;

        // 2. INFO 레벨: IP를 제외하고 요약 정보만 깔끔하게 출력
        log.info("[API] {} {} | Status: {} | Time: {}ms",
                method, fullPath, response.getStatus(), duration);

        // DEBUG 레벨: 상세 데이터 (개발 중에만 볼 내용)
        if (log.isDebugEnabled()) {
            String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
            String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

            if (!requestBody.isBlank()) {
                log.debug("Request Body  : {}", requestBody);
            }// if
            if (!responseBody.isBlank()) {
                log.debug("Response Body : {}", responseBody);
            } // if
        }
    }
}
