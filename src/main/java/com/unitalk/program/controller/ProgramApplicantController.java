package com.unitalk.program.controller;

import com.unitalk.program.model.dto.request.ProgramApplicantRequestDto;
import com.unitalk.program.model.dto.response.ProgramApplicantResponseDto;
import com.unitalk.program.service.ProgramApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramApplicantController {

    private final ProgramApplicantService programApplicantService;

    // 학생의 집단상담 신청 조회
    @GetMapping("/applicant/{studentNo}")
    public ResponseEntity<Page<ProgramApplicantResponseDto>> getStudentApplications(
            @PathVariable Long studentNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProgramApplicantResponseDto> applications = programApplicantService.getStudentApplications(studentNo, pageable);
        return ResponseEntity.ok(applications);
    }

    // 학생의 집단상담 신청 작성
    @PostMapping("/applicant-studnt")
    public ResponseEntity<ProgramApplicantResponseDto> createApplication(
            @RequestBody ProgramApplicantRequestDto requestDto) {
        ProgramApplicantResponseDto responseDto = programApplicantService.createApplication(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 전교생 집단상담 신청 목록 조회(교직원, 상담사)
    @GetMapping("/applicants")
    public ResponseEntity<Page<ProgramApplicantResponseDto>> getAllApplicants(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProgramApplicantResponseDto> applicants = programApplicantService.getAllApplicants(pageable);
        return  new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    // 집단상담 필터 및 검색
    @GetMapping("/applicants/search")
    public ResponseEntity<Page<ProgramApplicantResponseDto>> getProgramApplicantsByFilters(
            @RequestParam(required = false) Long programNo,
            @RequestParam(required = false) Long studentNo,
            @RequestParam(required = false) LocalDate applicantDate,
            @RequestParam(required = false) Long status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProgramApplicantResponseDto> applicants = programApplicantService.getProgramApplicantByFilters(
                        programNo, studentNo, applicantDate, status, pageable);
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    // 특정 학생의 집단상담 신청 작성(교직원, 상담사)
    /*
        @RequestHeader("employeeNo") Long employeeNo
        요청 보낸 헤더에서 직원번호 추출하여 비교, 직원만 작성 가능
    */
    @PostMapping("/applicant-employee")
    public ResponseEntity<ProgramApplicantResponseDto> createApplicationForStudent(
            @RequestBody ProgramApplicantRequestDto requestDto,
            @RequestHeader("employeeNo") Long employeeNo) {
        ProgramApplicantResponseDto responseDto = programApplicantService.createApplicationForStudent(requestDto, employeeNo);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 집단상담 신청 수정(삭제)
    @PatchMapping("/applicant/{applicantNo}")
    public ResponseEntity<ProgramApplicantResponseDto> cancelApplication(
            @PathVariable Long applicantNo,
            @RequestHeader("employeeNo") Long employeeNo) {
        ProgramApplicantResponseDto responseDto = programApplicantService.cancelApplication(applicantNo, employeeNo);
        return ResponseEntity.ok(responseDto);
    }

}
