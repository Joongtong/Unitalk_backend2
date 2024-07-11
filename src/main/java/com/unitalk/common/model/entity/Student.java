package com.unitalk.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "Students")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentNo")
public class Student {
    @Id
    @Column(name = "student_no")    // 학생번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNo;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true, nullable = false) // 학생 ID
    private User user;

    @Column(name = "reg_date", nullable = false)  // 입학일
    private LocalDate regDate;

    @Column(name = "grade", nullable = false) // 학년
    private Long grade;

    @ManyToOne
    @JoinColumn(name = "professor_no")  // 교수번호
    private Employee professor;

}