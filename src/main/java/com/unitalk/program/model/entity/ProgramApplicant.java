package com.unitalk.program.model.entity;

import com.unitalk.common.model.entity.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name = "Program_Applicants")
@AllArgsConstructor
@NoArgsConstructor
public class ProgramApplicant {
    @Id
    @Column(name = "applicant_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 집단상담 신청 번호
    private Long applicantNo;

    @ManyToOne
    @JoinColumn(name = "program_no", nullable = false) // 집단상담 번호
    private Program program;

    @ManyToOne
    @JoinColumn(name = "student_no", nullable = false) // 학생 번호
    private Student student;

    @CreatedDate
    @Column(name = "applicant_date", nullable = false, updatable = false) // 신청일
    private LocalDate applicantDate;

    @Column(name = "status", nullable = false) // 상태
    @ColumnDefault("1") // 1: 신청, 2: 취소
    private Long status;

}
