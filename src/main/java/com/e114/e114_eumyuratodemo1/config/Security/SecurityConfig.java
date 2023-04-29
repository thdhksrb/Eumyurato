package com.e114.e114_eumyuratodemo1.config.Security;

import com.e114.e114_eumyuratodemo1.config.JWT.JwtAuthenticationFilter;
import com.e114.e114_eumyuratodemo1.config.JWT.JwtTokenProvider;
import com.e114.e114_eumyuratodemo1.jdbc.CommonMemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CommonMemberDAO commonMemberDAO;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
/*              .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")*/
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, commonMemberDAO),
                        UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}


/*
*@EnableWebSecurity: Spring Security를 사용하기 위한 애노테이션입니다.
WebSecurityConfigurerAdapter: Spring Security에서 제공하는 클래스로 HTTP 요청에 대한 보안 설정을 구성합니다.
JwtTokenProvider, CommonMemberDAO: JWT 토큰을 이용한 인증을 위한 JwtTokenProvider 클래스와 유저 정보 조회를 위한 CommonMemberDAO 클래스를 필드로 가지고 있습니다.
passwordEncoder(): 비밀번호 암호화를 위해 PasswordEncoder를 Bean으로 등록합니다.
authenticationManagerBean(): Spring Security에서 인증 처리를 담당하는 AuthenticationManager를 Bean으로 등록합니다.
configure(): HttpSecurity를 통해 HTTP 요청에 대한 보안 설정을 구성합니다. httpBasic()과 csrf()를 각각 비활성화시키고, 세션을 사용하지 않도록 설정합니다. authorizeRequests()를 이용하여 경로에 대한 권한을 설정하고, addFilterBefore()를 이용하여 JWT 인증 필터를 추가합니다.
* */