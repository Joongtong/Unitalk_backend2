package com.unitalk.common.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "professor_assignments")
@Getter
@NoArgsConstructor
//지도교수 배정 이력 Entity
public class ProfessorAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_no", nullable = false)
    private Long assignmentNo; //지도교수 배정 이력 일련번호, PK

    @ManyToOne
    @JoinColumn(name = "professor_no", nullable = false, referencedColumnName = "employee_no")
    private Employee professorNo; //교수 ID(명시값 employeeId / 실제값 employeeNo), FK

    @ManyToOne
    @JoinColumn(name = "student_no", nullable = false, referencedColumnName = "student_no")
    private Student studentNo; //학생 ID(명시값 studentId / 실제값 studentNo), FK

    @Column(name = "assignment_date", nullable = false)
    private LocalDateTime assignmentDate; //지도교수 배정 일시

    @Builder
    public ProfessorAssignment(Employee professorNo, Student studentNo) {
        this.professorNo = professorNo;
        this.studentNo = studentNo;
    }

    //지도교수 배정 시점에 현재 시간을 assignmentDate로 설정
    @PrePersist
    protected void onCreate() {
        this.assignmentDate = LocalDateTime.now();
    }

}
