package com.unitalk.program.model.entity;

import com.unitalk.common.model.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "Program_Applicants")
public class ProgramApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicantId; // 집단상담 신청 번호(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programId", nullable = false)
    private Program programId; // 집단상담 번호(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentCode", referencedColumnName = "studentCode", nullable = false)
    private Student studentCode; // 학생 코드(FK)

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime applicantDate; // 신청일

    @Column(nullable = false)
    private Character status; // 신청상태
}
