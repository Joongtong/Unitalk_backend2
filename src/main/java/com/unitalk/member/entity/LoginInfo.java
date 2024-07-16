package com.unitalk.member.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.unitalk.common.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Login_Info")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "loginNo")
public class LoginInfo {

    @Id
    @Column(name = "login_no")  // 일련번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginNo;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false) // 사용자ID
    private User user;

    @Column(name = "pwd", nullable = false) // 비밀번호
    private String pwd;

    // 사용자구분 : ROLE_P 프로페서, ROLE_S 스튜던트, ROLE_C 카운셀러, ROLE_E 임플로이
    @Column(name = "role", nullable = false) // 비밀번호
    private String role;

    @Column(name = "user_type", nullable = false)
    private String userType;

    public void setRole(String role) {
        this.role = "ROLE_" + role;
    }
}
