package com.unitalk.counseling.model.dto.response;

import com.unitalk.common.model.entity.Student;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingReviewResponseDto {

    private Long reviewNo;
    private Long reqNo;
    private Student student;
    private BigDecimal rating;
    private String content;

}