package com.unitalk.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Students")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "studentNo")
public class Student {

    @Id
    @Column(name = "student_no")    // 학생일련번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNo;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true, nullable = false) // 학생번호
    private User user;

    @Column(name = "reg_date", nullable = false)  // 입학일
    private LocalDate regDate;

    @Column(name = "grade", nullable = false) // 학년
    private Long grade;

    @ManyToOne
    @JoinColumn(name = "professor_no")  // 교수일련번호
    private Employee professor;

    @Builder
    public Student(User user, LocalDate regDate, Long grade) {
        this.user = user;
        this.regDate = regDate;
        this.grade = grade;
    }

    //Setter for 지도교수 배정
    public void setProfessorNo(Employee professor) {
        this.professor = professor;
    }

}
