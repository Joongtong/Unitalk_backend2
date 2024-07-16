package com.unitalk.member.controller;

import com.unitalk.common.model.entity.User;
import com.unitalk.member.dto.CustomUserDetails;
import com.unitalk.member.dto.LoginRequest;
import com.unitalk.member.entity.LoginInfo;
import com.unitalk.member.jwt.JWTUtil;
import com.unitalk.member.response.JwtResponse;
import com.unitalk.member.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * 로그인 요청을 처리하여 사용자를 인증하고 JWT 토큰을 생성합니다.
     *
     * @param loginRequest 사용자 이름과 비밀번호를 포함한 로그인 요청
     * @return 사용자 이름, 역할, JWT 토큰을 포함한 JwtResponse
     */
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Controller/ User ID: " + loginRequest.getUserId() + ", Password: " + loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId().toString(),
                        loginRequest.getPassword()
                )
        );

        // SecurityContextHolder에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // UserDetails를 통해 LoginInfo 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(loginRequest.getUserId().toString());
        LoginInfo loginInfo = userDetails.getLoginInfo();
        User user = loginInfo.getUser();
        String userType = loginInfo.getUserType();

        // 로그인 요청에서 제공된 역할을 사용하여 JWT 토큰 생성
        String token = jwtUtil.createJwt(loginRequest.getUserId().toString(), loginInfo.getRole(), userType, 3600L);
        System.out.println("#LoginController / login / token: " + token);

        // 역할도 포함된 응답 생성
        Long entityNo = null;
        if (user.getStudent() != null) {
            entityNo = user.getStudent().getStudentNo();
        } else if (user.getEmployee() != null) {
            entityNo = user.getEmployee().getEmployeeNo();
        }
        return new JwtResponse(
                loginRequest.getUserId().toString(),
                user.getUserName(), // Ensure the user name is correctly retrieved
                loginInfo.getRole(),
                userType,
                token,
                entityNo
        );
    }

}
