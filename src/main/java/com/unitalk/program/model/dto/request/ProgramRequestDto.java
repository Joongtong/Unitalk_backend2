package com.unitalk.program.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unitalk.program.model.entity.Program;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequestDto {
    private Long counselorNo; // 상담사
    private String programName; // 집단상담명
    private String programContent; // 집단상담 내용
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recruitStart; // 모집시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recruitEnd; // 모집종료일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operationStart; // 운영시작일
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operationEnd; // 운영종료일
    private Long programSession; // 회차
    private Long recruitNum; // 모집인원
    private Long status; // 상태
    private Long viewCnt; // 조회수
}
