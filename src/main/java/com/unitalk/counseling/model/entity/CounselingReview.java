package com.unitalk.counseling.model.entity;

import com.unitalk.common.model.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "Counseling_Reviews")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;

    @ManyToOne
    @JoinColumn(name = "req_no")
    private Counseling reqCounseling;

    @ManyToOne
    @JoinColumn(name = "student_no")
    private Student student;

    private BigDecimal rating;

    private String content;

}
