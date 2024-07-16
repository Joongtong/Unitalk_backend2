package com.unitalk.member.service;

import com.unitalk.common.model.entity.User;
import com.unitalk.member.dto.JoinDTO;
import com.unitalk.member.entity.LoginInfo;
import com.unitalk.member.repository.LoginInfoRepository;
import com.unitalk.member.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원가입 처리를 담당하는 서비스 클래스입니다.
 */
@Service
public class JoinService {

    private final LoginInfoRepository loginInfoRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * JoinService 생성자입니다.
     *
     * @param loginInfoRepository 로그인 정보 리포지토리
     * @param userRepository 사용자 리포지토리
     * @param bCryptPasswordEncoder 비밀번호 암호화를 위한 BCryptPasswordEncoder
     */
    public JoinService(LoginInfoRepository loginInfoRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.loginInfoRepository = loginInfoRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 회원가입 처리를 수행합니다.
     *
     * @param joinDTO 회원가입 정보를 담은 DTO
     */
    public void joinProcess(JoinDTO joinDTO) {
        Long userId = joinDTO.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        String password = joinDTO.getPassword();
        String role = joinDTO.getRole();
        String userType = joinDTO.getUserType();

        // User를 조회합니다.
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId);
        }

        Boolean isExist = loginInfoRepository.existsByUserUserId(userId);

        if (isExist) {
            return;
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(user);
        loginInfo.setPwd(bCryptPasswordEncoder.encode(password));
        loginInfo.setRole(role);
        loginInfo.setUserType(userType);

        loginInfoRepository.save(loginInfo);
    }
}
