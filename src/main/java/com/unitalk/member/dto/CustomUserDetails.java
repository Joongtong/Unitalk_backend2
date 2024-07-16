package com.unitalk.member.dto;

import com.unitalk.common.model.entity.User;
import com.unitalk.member.entity.LoginInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * UserDetails 인터페이스를 구현한 CustomUserDetails 클래스입니다.
 */
public class CustomUserDetails implements UserDetails {

    private final LoginInfo loginInfo;

    public CustomUserDetails(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    /**
     * 사용자의 권한을 반환합니다.
     *
     * @return GrantedAuthority의 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> loginInfo.getRole());
        return collection;
    }

    /**
     * 사용자의 비밀번호를 반환합니다.
     *
     * @return 비밀번호 문자열
     */
    @Override
    public String getPassword() {
        return loginInfo.getPwd();
    }

    /**
     * 사용자의 이름을 반환합니다.
     *
     * @return 사용자 이름 문자열
     */
    @Override
    public String getUsername() {
        return loginInfo.getUser().getUserId().toString();
    }

    /**
     * 사용자의 역할을 반환합니다.
     *
     * @return 역할 문자열
     */
    public String getRole() {
        return loginInfo.getRole();
    }

    /**
     * 사용자의 타입을 반환합니다.
     *
     * @return 타입 문자열
     */
    public String getUserType() {
        return loginInfo.getUserType();
    }

    /**
     * LoginInfo 객체를 반환합니다.
     *
     * @return LoginInfo 객체
     */
    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    /**
     * User 객체를 반환합니다.
     *
     * @return User 객체
     */
    public User getUser() {
        return loginInfo.getUser();
    }

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return true (항상 만료되지 않음)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠기지 않았는지 여부를 반환합니다.
     *
     * @return true (항상 잠기지 않음)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return true (항상 만료되지 않음)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화되어 있는지 여부를 반환합니다.
     *
     * @return true (항상 활성화됨)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
