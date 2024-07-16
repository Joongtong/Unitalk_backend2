package com.unitalk.member.service;

import com.unitalk.common.model.entity.User;
import com.unitalk.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getUsernameById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getUserName(); // Lombok이 생성한 getter 사용
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId);
        }
    }
}
