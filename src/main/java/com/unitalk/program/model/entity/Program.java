package com.unitalk.program.model.entity;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.program.model.dto.response.ProgramResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "Program")
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    @Id
    @Column(name = "program_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 집단상담 번호
    private Long programNo;

    @ManyToOne
    @JoinColumn(name = "counselor_no", nullable = false) // 상담사 번호
    private Employee counselor;

    @Column(name = "program_name", nullable = false) // 집단상담명
    private String programName;

    @Column(name = "program_content", nullable = false) // 집단상담 내용
    private String programContent;

    @Column(name = "recruit_start") // 모집시작일
    private LocalDate recruitStart;

    @Column(name = "recruit_end") // 모집종료일
    private LocalDate recruitEnd;

    @Column(name = "operation_start") // 운영시작일
    private LocalDate operationStart;

    @Column(name = "operation_end") // 운영종료일
    private LocalDate operationEnd;

    @Column(name = "program_session") // 회차
    @ColumnDefault("1") // 기본값 1
    private Long programSession;

    @Column(name = "recruit_num") // 모집인원
    private Long recruitNum;

    @Column(name = "status", nullable = false) // 상태
    private Long status; // 1: 신청가능, 2: 신청불가

    @Column(name = "view_cnt")
    @ColumnDefault("0") // 기본값 0
    private Long viewCnt; // 조회수

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProgramFile> programFiles; // 집단상담 파일(이미지)-삭제용

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramApplicant> applicants; // 집단상담 신청-삭제용

    // 엔티티 필드 업데이트
    public void update(String programName, String programContent, LocalDate recruitStart, LocalDate recruitEnd,
                       LocalDate operationStart, LocalDate operationEnd, Long programSession,
                       Long recruitNum, Long status, Long viewCnt) {
        this.programName = programName;
        this.programContent = programContent;
        this.recruitStart = recruitStart;
        this.recruitEnd = recruitEnd;
        this.operationStart = operationStart;
        this.operationEnd = operationEnd;
        this.programSession = programSession;
        this.recruitNum = recruitNum;
        this.status = status;
        this.viewCnt = viewCnt;
    }


    public void setCounselor(Employee counselor) {
        this.counselor = counselor;
    }

    // 조회수 증가
    public void viewCount() {
        this.viewCnt++;
    }

    // 엔티티를 DTO로 변환
    public ProgramResponseDto toDto() {
        return ProgramResponseDto.builder()
                .programNo(programNo)
                .counselor(counselor)
                .programName(programName)
                .programContent(programContent)
                .recruitStart(recruitStart)
                .recruitEnd(recruitEnd)
                .operationStart(operationStart)
                .operationEnd(operationEnd)
                .programSession(programSession)
                .recruitNum(recruitNum)
                .status(status)
                .viewCnt(viewCnt)
                .build();
    }

}