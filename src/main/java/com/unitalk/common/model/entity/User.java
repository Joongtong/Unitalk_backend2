package com.unitalk.common.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @Column(name = "user_id")   // 사용자ID
    private Long userId;
    
    @ManyToOne
    @JoinColumn(name = "detp_id", nullable = false) // 부서코드
    private Department department;  
    
    @Column(name = "user_name", nullable = false)   // 이름
    private String userName;
    
    @Column(name = "tel")   // 전화번호
    private String tel;
    
    @Column(name = "email") // 이메일
    private String email;
    
    @Column(name = "user_type", nullable = false)   // 사용자 구분
    private String userType;
    
}
