package com.bnw.beta.domain.member.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class MemberDTO implements UserDetails {
    private Integer member_no;
    private String member_id;
    private String member_pw;
    private String member_name;
    private String member_birth;
    private String member_email;
    private String member_gender;
    private String member_phone;
    private String member_tell;
    private String member_agreeM;
    private String member_agreeP;
    private String member_isshow;
    private String role;
    private String license;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate join_date;
    private String pay_count;
    private String edu_code;

    private String currentPassword; // 현재 비밀번호 김현민
    private String newPassword; // 새로운 비밀번호 김현민
    private String confirmPassword; // 새로운 비밀번호 확인 김현민


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보 반환 (권한이 여러 개인 경우 Collection으로 반환)
        return null; // 해당 예시에서는 권한 정보가 없으므로 null 반환
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 여부 반환
        return true; // 계정 만료 기능을 사용하지 않으므로 항상 true 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨있지 않은지 여부 반환
        return true; // 계정 잠금 기능을 사용하지 않으므로 항상 true 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호가 만료되지 않았는지 여부 반환
        return true; // 비밀번호 만료 기능을 사용하지 않으므로 항상 true 반환
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었는지 여부 반환
        return true;  // 활성화된 계정인 경우 항상 true 반환
    }
}