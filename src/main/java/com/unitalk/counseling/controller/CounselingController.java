package com.unitalk.counseling.controller;

import com.unitalk.counseling.model.dto.CounselingCountsDto;
import com.unitalk.counseling.model.dto.request.CounselingRequestDto;
import com.unitalk.counseling.model.dto.request.CounselingUpdateDto;
import com.unitalk.counseling.model.dto.response.CounselingResponseDto;
import com.unitalk.counseling.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/counselings")
@RequiredArgsConstructor
public class CounselingController {

    private final CounselingService counselingService;

    // 새로운 상담 신청
    @PostMapping
    public ResponseEntity<CounselingResponseDto> createCounseling(@RequestBody CounselingRequestDto requestDto) {
        CounselingResponseDto responseDto = counselingService.createCounseling(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 상담 내용 업데이트
    @PutMapping("/{reqNo}")
    public ResponseEntity<CounselingResponseDto> updateCounseling(
            @PathVariable long reqNo,
            @RequestBody CounselingUpdateDto updateDto) {
        CounselingResponseDto responseDto = counselingService.updateCounseling(reqNo, updateDto);
        return ResponseEntity.ok(responseDto);
    }

    // 상담 정보 개별 조회
    @GetMapping("/{reqNo}")
    public ResponseEntity<CounselingResponseDto> getCounselingByNo(@PathVariable long reqNo) {
        CounselingResponseDto responseDto = counselingService.getCounselingById(reqNo);
        return ResponseEntity.ok(responseDto);
    }

    // 학생의 모든 상담 정보 조회
    @GetMapping("/student/{studentNo}")
    public ResponseEntity<Page<CounselingResponseDto>> getCounselingsByStudentNo(
            @PathVariable Long studentNo,
            @RequestParam(required = false) Long counselMode,
            @RequestParam(required = false) Long status,
            @RequestParam(required = false) String counselType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {

        Page<CounselingResponseDto> counselings = counselingService.getCounselingsByStudentNoWithFilters(
                studentNo, counselMode, status, counselType, startDate, endDate, pageable);
        return ResponseEntity.ok(counselings);
    }

    // 상담 횟수 조회
    @GetMapping("/student/{studentNo}/counts")
    public ResponseEntity<CounselingCountsDto> getCounselingsByStudentNo(@PathVariable Long studentNo) {
        CounselingCountsDto counts = counselingService.getCounselingCountsByStudentNo(studentNo);
        return ResponseEntity.ok(counts);
    }

    // 상담사의 모든 상담 정보 조회
    @GetMapping("/counselor/{counselorNo}")
    public ResponseEntity<Map<String, Object>> getCounselingsByCounselorNo(@PathVariable Long counselorNo,
                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CounselingResponseDto> responseDtos = counselingService.getCounselingsByCounselorNo(counselorNo, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", responseDtos.getContent());
        response.put("currentPage", responseDtos.getNumber());
        response.put("totalPages", responseDtos.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/counselor/{counselorNo}/filtered")
    public ResponseEntity<Page<CounselingResponseDto>> getFilteredCounselingsByCounselorNo(
            @PathVariable Long counselorNo,
            @RequestParam(required = false) Long status,
            @RequestParam(required = false) Boolean hasResult,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false, defaultValue = "latest") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CounselingResponseDto> responseDtos = counselingService.getFilteredCounselingsByCounselorNo(
                counselorNo, status, hasResult, searchQuery, sortOrder, pageable);

        return ResponseEntity.ok(responseDtos);
    }

}