package com.unitalk.program.model.dto.response;

import com.unitalk.common.model.entity.Student;
import com.unitalk.program.model.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramApplicantResponseDto {
    private Long applicantNo; // 집단상담 신청 번호
    private Program program; // 집단상담
    private Student student; // 학생
    private LocalDate applicantDate; // 집단상담 신청일
    private Long status; // 상태
}
