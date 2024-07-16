package com.unitalk.member.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String userId;
    private String userName; // Added userName field
    private String role;
    private String userType;
    private String token;
    private Long entityNo;

    public JwtResponse(String userId, String userName, String role, String userType, String token, Long entityNo) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
        this.userType = userType;
        this.token = token;
        this.entityNo = entityNo;
    }
}
