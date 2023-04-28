package com.e114.e114_eumyuratodemo1.config.JWT;

import com.e114.e114_eumyuratodemo1.dto.CommonMemberDTO;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor // 필수적인 final 필드 값을 초기화하는 생성자를 생성
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter 를 상속

    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider 를 사용하기 위한 필드
    private final CommonMemberDAO commonMemberDAO; // CommonMemberDAO 를 사용하기 위한 필드

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 헤더에서 JWT 를 받아옴
        String token = jwtTokenProvider.resolveToken(request);

        // 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            CommonMemberDTO commonMemberDTO = commonMemberDAO.findById(jwtTokenProvider.getUserPk(token));

            // SecurityContext 에 Authentication 객체를 저장
            // Authentication 객체는 현재 사용자를 나타내며, 인증된 사용자는 SecurityContextHolder 에 저장
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(commonMemberDTO, "", commonMemberDTO.getAuthorities()));
        }
        chain.doFilter(request, response); // 요청과 응답을 FilterChain 에게 전달
    }
}


/*
JwtAuthenticationFilter: JWT 토큰 인증 필터를 구현한 클래스입니다.
OncePerRequestFilter: 한번의 요청에 대해 필터가 한번만 실행되도록 하는 스프링 프레임워크의 클래스입니다.
jwtTokenProvider: JWT 토큰을 처리하는데 사용되는 JwtTokenProvider 객체입니다.
commonMemberDAO: 사용자 정보를 데이터베이스에서 조회하는데 사용되는 CommonMemberDAO 객체입니다.
doFilterInternal(): 요청을 필터링하는 메서드입니다. OncePerRequestFilter 클래스에서 구현되어 있으며, 구현해야 할 추상 메서드입니다.
jwtTokenProvider.resolveToken(request): 요청 헤더에서 JWT 토큰을 추출합니다.
jwtTokenProvider.validateToken(token): 추출한 JWT 토큰이 유효한지 확인합니다.
commonMemberDAO.findById(jwtTokenProvider.getUserPk(token)): 추출한 JWT 토큰에서 사용자 ID를 추출하여 데이터베이스에서 사용자 정보를 조회합니다.
SecurityContextHolder.getContext().setAuthentication(): 인증된 사용자 정보를 SecurityContext 에 저장합니다. commonMemberDTO 를 인증 객체에 담아 저장합니다.
chain.doFilter(request, response): 요청과 응답을 다음 필터나 서블릿으로 전달합니다.*/
