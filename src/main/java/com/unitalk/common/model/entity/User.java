package com.unitalk.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.unitalk.member.entity.LoginInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

    @Id
    @Column(name = "user_id")   // 사용자ID
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false) // 부서코드
    private Department department;

    @Column(name = "user_name", nullable = false)   // 이름
    private String userName;

    @Column(name = "tel")   // 전화번호
    private String tel;

    @Column(name = "email") // 이메일
    private String email;

    @Column(name = "user_type", nullable = false)   // 사용자 구분
    private String userType; // 사용자구분 : P 프로페서, S 스튜던트, C 카운셀러, E 임플로이

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // 양방향 매핑
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // 양방향 매핑
    private Employee employee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // 양방향 매핑
    private LoginInfo loginInfo;

}
