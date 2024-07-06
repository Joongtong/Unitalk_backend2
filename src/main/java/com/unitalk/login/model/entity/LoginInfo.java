package com.unitalk.login.model.entity;

import com.unitalk.common.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Login_Info")
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    @Id
    @Column(name = "login_no")  // 일련번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginNo;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // 사용자ID
    private User user;

    @Column(name = "pwd", nullable = false) // 비밀번호
    private String pwd;

}
