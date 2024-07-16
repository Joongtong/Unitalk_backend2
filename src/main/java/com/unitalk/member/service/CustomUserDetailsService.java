package com.unitalk.member.service;

import com.unitalk.member.dto.CustomUserDetails;
import com.unitalk.member.entity.LoginInfo;
import com.unitalk.member.repository.LoginInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 세부 정보를 제공하는 서비스 클래스입니다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginInfoRepository loginInfoRepository;

    /**
     * CustomUserDetailsService 생성자입니다.
     *
     * @param loginInfoRepository 로그인 정보 리포지토리
     */
    public CustomUserDetailsService(LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }

    /**
     * 주어진 사용자 이름에 대한 사용자 세부 정보를 로드합니다.
     *
     * @param username 사용자 이름
     * @return 사용자 세부 정보
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginInfo loginInfo = loginInfoRepository.findByUserUserId(Long.parseLong(username));
        if (loginInfo == null) {
            System.out.println("CustomUserDetailsService/ 조회된 사용자가 없습니다: " + username);
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        return new CustomUserDetails(loginInfo);
    }
}
