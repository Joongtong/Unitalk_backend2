package com.unitalk.program.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unitalk.program.model.dto.request.ProgramRequestDto;
import com.unitalk.program.model.dto.response.ProgramResponseDto;
import com.unitalk.program.service.ProgramFileService;
import com.unitalk.program.service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;
    private final ProgramFileService programFileService;

    // 집단상담 목록 조회
    @GetMapping("/programs")
    public ResponseEntity<Page<ProgramResponseDto>> getAllPrograms(Pageable pageable) {
        Page<ProgramResponseDto> programs = programService.getAllPrograms(pageable);
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    // 집단상담 필터 및 검색
    @GetMapping("/programs/search")
    public ResponseEntity<Page<ProgramResponseDto>> getProgramsByFilters(
            @RequestParam(required = false) Long counselorNo,
            @RequestParam(required = false) String programName,
            @RequestParam(required = false) String programContent,
            @RequestParam(required = false) LocalDate recruitStart,
            @RequestParam(required = false) LocalDate recruitEnd,
            @RequestParam(required = false) LocalDate operationStart,
            @RequestParam(required = false) LocalDate operationEnd,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long viewCnt,
            Pageable pageable) {

        Page<ProgramResponseDto> programs = programService.getProgramsByFilters(
                counselorNo, programName, programContent, recruitStart, recruitEnd,
                operationStart, operationEnd, status, viewCnt, pageable);
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    // 집단상담 조회
    @GetMapping("/program/{programNo}")
    public ResponseEntity<ProgramResponseDto> getProgramById(@PathVariable Long programNo) {
        ProgramResponseDto program = programService.getProgramById(programNo);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }

    // 집단상담 작성
    @PostMapping("/program")
    public ResponseEntity<ProgramResponseDto> createProgram(
            @Valid @ModelAttribute("program") String programJson,
            @RequestPart("files") List<MultipartFile> files,
            @RequestHeader("employeeNo") Long creatorEmployeeNo) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ProgramRequestDto requestDto = objectMapper.readValue(programJson, ProgramRequestDto.class);

        ProgramResponseDto program = programService.createProgram(requestDto, creatorEmployeeNo);
        programFileService.saveFiles(files, program.getProgramNo());

        return new ResponseEntity<>(program, HttpStatus.CREATED);
    }

    // 집단상담 수정
    @PutMapping("/program/{programNo}")
    public ResponseEntity<ProgramResponseDto> updateProgram(
            @PathVariable Long programNo,
            @Valid @ModelAttribute("program") String programJson,
            @RequestPart("files") List<MultipartFile> files,
            @RequestHeader("employeeNo") Long updaterEmployeeNo) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ProgramRequestDto requestDto = objectMapper.readValue(programJson, ProgramRequestDto.class);

        ProgramResponseDto program = programService.updateProgram(programNo, requestDto, updaterEmployeeNo);
        programFileService.saveFiles(files, program.getProgramNo());

        return new ResponseEntity<>(program, HttpStatus.OK);
    }

    // 집단상담 삭제
    @DeleteMapping("/program/{programNo}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programNo) {
        programService.deleteProgram(programNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
