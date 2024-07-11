package com.unitalk.program.model.dto.response;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.program.model.dto.ProgramFileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramResponseDto {
    private Long programNo; // 집단상담 번호
    private Employee counselor; // 상담사
    private String programName; // 집단상담명
    private String programContent; // 집단상담 내용
    private LocalDate recruitStart; // 모집시작일
    private LocalDate recruitEnd; // 모집종료일
    private LocalDate operationStart; // 운영시작일
    private LocalDate operationEnd; // 운영종료일
    private Long programSession; // 회차
    private Long recruitNum; // 모집인원
    private Long status; // 상태
    private Long viewCnt; // 조회수

    private ProgramFileDto thumbnailFile; //썸네일 파일
    private List<ProgramFileDto> files; // 파일
}
