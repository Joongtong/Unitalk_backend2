package com.unitalk.counseling.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.unitalk.common.model.entity.Department;
import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.Student;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "counseling")
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reqNo")
public class Counseling {

    @Id
    @Column(name = "req_no")    // 상담신청번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reqNo;

    @ManyToOne
    @JoinColumn(name = "student_no", nullable = false)  // 학생일련번호
    private Student student;

    @ManyToOne
    @JoinColumn(name = "counselor_no", nullable = false)    // 상담사일련번호
    private Employee counselor;    // 상담사ID

    @ManyToOne
    @JoinColumn(name = "sch_no")
    private CounselorSchedule schedule; // 시간표번호

    @Column(name = "counsel_date", nullable = false)    // 상담일
    private LocalDate counselDate;

    @CreatedDate
    @Column(name ="application_date", nullable = false, updatable = false)  // 신청일시
    private LocalDateTime applicationDate;

    @Column(name = "counsel_mode", nullable = false)    // 대면1 | 비대면2
    private Long counselMode;

    @ManyToOne
    @JoinColumn(name = "counsel_type", nullable = false) // 상담분야
    private Department department;

    @Column(name = "application_content")   // 신청내용
    private String applicationContent;

    @Column(name = "status", nullable = false)  // 진행상태
    private Long status;

    @Column(name = "counsel_content")   // 상담기록(상담사가 작성함)
    private String counselContent;

    @LastModifiedDate
    @Column(name = "record_time")   // 상담기록을 작성한 시각
    private LocalDateTime recordTime;

    @ManyToOne
    @JoinColumn(name = "previous_req_no")   // 이전상담번호
    private Counseling previousReqCounseling;

}
