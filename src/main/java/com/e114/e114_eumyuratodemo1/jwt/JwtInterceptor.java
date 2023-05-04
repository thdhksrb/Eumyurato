package com.e114.e114_eumyuratodemo1.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 토큰 받기
//        System.out.println("preHandle 실행!");
        String accessToken = jwtUtils.getAccessToken(request);
        System.out.println("Interceptor accessToken : " + accessToken);
        // 로깅용 URI
        String requestURI = request.getRequestURI();

        // 비회원일 때
        if(accessToken == null){
            logger.debug("비회원 유저입니다 URI: {}", requestURI);
            return true;

        }else{ // 액세스,  있을 때
            logger.debug("access, refresh 토큰 둘 다 존재합니다.");
            if(jwtUtils.validateToken(accessToken)) {
                //accesstoke이 유효할 때
                logger.debug("유효한 access 토큰 정보입니다. URI: {}", requestURI);
                return true;
            } else {
                logger.debug("인증 실패");
                return false;
            }
        }



    }

}
