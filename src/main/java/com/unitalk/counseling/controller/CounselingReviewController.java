package com.unitalk.counseling.controller;

import com.unitalk.counseling.model.dto.request.CounselingReviewRequestDto;
import com.unitalk.counseling.model.dto.response.CounselingReviewResponseDto;
import com.unitalk.counseling.service.CounselingReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counseling-reviews")
@RequiredArgsConstructor
public class CounselingReviewController {

    private final CounselingReviewService reviewService;

    // 새로운 상담 후기 생성
    @PostMapping
    public ResponseEntity<CounselingReviewResponseDto> createReview(@RequestBody CounselingReviewRequestDto requestDto) {
        CounselingReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 상담 후기 개별 조회
    @GetMapping("/{reviewNo}")
    public ResponseEntity<CounselingReviewResponseDto> getReviewById(@PathVariable long reviewNo) {
        CounselingReviewResponseDto responseDto = reviewService.getReviewById(reviewNo);
        return ResponseEntity.ok(responseDto);
    }

    // 학생의 모든 상담 후기 조회
    @GetMapping("/student/{studentNo}")
    public ResponseEntity<List<CounselingReviewResponseDto>> getReviewsByStudentNo(@PathVariable long studentNo) {
        List<CounselingReviewResponseDto> responseDtos = reviewService.getReviewsByStudentNo(studentNo);
        return ResponseEntity.ok(responseDtos);
    }

    // 특정 상담에 대한 모든 리뷰 조회
    @GetMapping("/counseling/{reqNo}")
    public ResponseEntity<List<CounselingReviewResponseDto>> getReviewsByCounselingNo(@PathVariable long reqNo) {
        List<CounselingReviewResponseDto> responseDtos = reviewService.getReviewsByCounselingNo(reqNo);
        return ResponseEntity.ok(responseDtos);
    }
}