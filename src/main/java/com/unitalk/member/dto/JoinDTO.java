package com.unitalk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    private Long userId;
    private String password;
    private String role;
    private String userType;
}
