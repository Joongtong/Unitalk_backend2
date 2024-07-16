package com.unitalk.counseling.model.entity;

import com.unitalk.common.model.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Counselor_Schedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorSchedule {

    @Id
    @Column(name = "sch_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schNo;

    @ManyToOne
    @JoinColumn(name = "counselor_no", nullable = false)
    private Employee counselor;

    private String days;    // 요일
    private Long availTime; // 학교의 1~9교시를 숫자로 저장.
    private Long status;    // 예약상태(활성1|비활성2)

}
