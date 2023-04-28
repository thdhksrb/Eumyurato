package com.e114.e114_eumyuratodemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonMemberDTO{
    private String id;
    private String pwd;
    private String name;
    private String nid;
    private String sex;
    private String birth;
    private String email;
    private String phone;
    private String road;
    private String genre;
    private int adminNum;
    private Map<String,String> favorite;
    private boolean kakao;
    private boolean google;
    private String image;

    // Spring Security에서는 권한 정보를 Collection 형태의 List<GrantedAuthority>으로 사용
    private List<GrantedAuthority> authorities;

    // 해당 DTO 클래스에서도 권한 정보를 List<GrantedAuthority> 타입의 authorities 필드로 선언해주고
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    // 이를 위해 getAuthorities() 메서드로 authorities 필드를 반환하고, setAuthorities() 메서드로 authorities 필드를 설정
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    // setRoles() 메서드는 실제로 사용하지 않는 빈 메서드로, authorities 필드를 설정하는 setAuthorities() 메서드를 대신 사용
    public void setRoles(List<String> roles) {
    }
}
