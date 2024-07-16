package com.unitalk.program.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramApplicantRequestDto {
    private Long programNo; // 집단상담 번호
    private Long studentNo; // 학생 번호
    @JsonFormat(pattern = "yyyy-MM-dd") // 집단상담 신청일
    private LocalDate applicantDate;
    private Long status; // 상태
}
