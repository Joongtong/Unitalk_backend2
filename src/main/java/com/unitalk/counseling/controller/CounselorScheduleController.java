package com.unitalk.counseling.controller;

import com.unitalk.counseling.model.dto.request.CounselorScheduleRequestDto;
import com.unitalk.counseling.model.dto.response.CounselorScheduleResponseDto;
import com.unitalk.counseling.service.CounselorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counselor-schedules")
@RequiredArgsConstructor
public class CounselorScheduleController {

    @Autowired
    private CounselorScheduleService counselorScheduleService;

    // 상담사 스케쥴 등록
    @PostMapping
    public ResponseEntity<CounselorScheduleResponseDto> registerSchedule(@RequestBody CounselorScheduleRequestDto requestDto) {
        CounselorScheduleResponseDto savedSchedule = counselorScheduleService.registerSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }

    // 상담사의 모든 스케쥴 조회
    @GetMapping("/{counselorNo}")
    public ResponseEntity<List<CounselorScheduleResponseDto>> getSchedulesByCounselorId(@PathVariable long counselorNo) {
        List<CounselorScheduleResponseDto> schedules = counselorScheduleService.getSchedulesByCounselorNo(counselorNo);
        return ResponseEntity.ok(schedules);
    }

    // 특정 스케쥴 삭제
    @DeleteMapping("/{schNo}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable long schNo) {
        counselorScheduleService.deleteSchedule(schNo);
        return ResponseEntity.noContent().build();
    }

    // 특정 스케쥴 업데이트
    @PutMapping("/{schNo}")
    public ResponseEntity<CounselorScheduleResponseDto> updateSchedule(
            @PathVariable long schNo,
            @RequestBody CounselorScheduleRequestDto scheduleDTO) {
        CounselorScheduleResponseDto updatedSchedule = counselorScheduleService.updateSchedule(schNo, scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

}