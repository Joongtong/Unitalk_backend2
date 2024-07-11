package com.unitalk.program.service;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.repository.EmployeeRepository;
import com.unitalk.program.model.dto.ProgramFileDto;
import com.unitalk.program.model.dto.request.ProgramRequestDto;
import com.unitalk.program.model.dto.response.ProgramResponseDto;
import com.unitalk.program.model.entity.Program;
import com.unitalk.program.model.entity.ProgramFile;
import com.unitalk.program.repository.ProgramFileRepository;
import com.unitalk.program.repository.ProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final EmployeeRepository employeeRepository;
    private final ProgramFileRepository programFileRepository;

    // 집단상담 목록 조회
    /*
        Stream API 메서드: map(Program::toDto);
        Program의 toDto를 참조한다.
    */
    public Page<ProgramResponseDto> getAllPrograms(Pageable pageable) {
        Page<Program> programs = programRepository.findAllByOrderByProgramNoDesc(pageable);
        return programs.map(this::ProgramDto);
    }

    // 집단상담 필터 및 검색
    public Page<ProgramResponseDto> getProgramsByFilters(Long counselorNo, String programName, String programContent,
                                                            LocalDate recruitStart, LocalDate recruitEnd,
                                                            LocalDate operationStart, LocalDate operationEnd,
                                                            Long status, Long viewCnt, Pageable pageable) {

        Page<Program> programs = programRepository.findByFilters(counselorNo, programName, programContent,
                                                                    recruitStart, recruitEnd,
                                                                    operationStart, operationEnd, status, viewCnt, pageable);
        return programs.map(this::ProgramDto);
    }

    // 집단상담 조회
    public ProgramResponseDto getProgramById(Long programNo) {
        Program program = programRepository.findById(programNo)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다." + programNo));

        // 조회수 증가
        program.viewCount();
        programRepository.save(program);

        return ProgramDto(program);
    }

    // 집단상담 작성
    @Transactional
    public ProgramResponseDto createProgram(ProgramRequestDto requestDto, Long employeeNo) {
        // 프로그램 입력 요청자의 정보 조회 (교직원만 가능)
        Employee employee = employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 교직원 정보가 없습니다." + employeeNo));

        // 교직원 검증
        if (!employee.isStaff()) {
            throw new IllegalArgumentException("교직원만 프로그램을 입력할 수 있습니다.");
        }

        // 상담사 정보 조회
        Employee counselor = employeeRepository.findById(requestDto.getCounselorNo())
                .orElseThrow(() -> new EntityNotFoundException("해당 상담사 정보가 없습니다." + requestDto.getCounselorNo()));

        if (!counselor.isCounselor()) {
            throw new IllegalArgumentException("상담사만 상담사로 지정할 수 있습니다.");
        }

        Program program = Program.builder()
                .counselor(counselor)
                .programName(requestDto.getProgramName())
                .programContent(requestDto.getProgramContent())
                .recruitStart(requestDto.getRecruitStart())
                .recruitEnd(requestDto.getRecruitEnd())
                .operationStart(requestDto.getOperationStart())
                .operationEnd(requestDto.getOperationEnd())
                .programSession(requestDto.getProgramSession())
                .recruitNum(requestDto.getRecruitNum())
                .status(requestDto.getStatus())
                .viewCnt(requestDto.getViewCnt())
                .build();

        program = programRepository.save(program);
        return program.toDto();
    }

    // 집단상담 수정
    @Transactional
    public ProgramResponseDto updateProgram(Long programNo, ProgramRequestDto requestDto, Long employeeNo) {
        Program program = programRepository.findById(programNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다." + programNo));

        // 프로그램 수정 요청자의 정보 조회 (교직원만 가능)
        Employee employee = employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 교직원 정보가 없습니다." + employeeNo));

        // 교직원 검증
        if (!employee.isStaff()) {
            throw new IllegalArgumentException("교직원만 프로그램을 수정할 수 있습니다.");
        }

        // 상담사 정보 조회
        Employee counselor = employeeRepository.findById(requestDto.getCounselorNo())
                .orElseThrow(() -> new EntityNotFoundException("해당 상담사 정보가 없습니다." + requestDto.getCounselorNo()));

        // 상담사 검증
        if (!counselor.isCounselor()) {
            throw new IllegalArgumentException("상담사만 상담사로 지정할 수 있습니다.");
        }

        program.update(requestDto.getProgramName(), requestDto.getProgramContent(),
                requestDto.getRecruitStart(), requestDto.getRecruitEnd(),
                requestDto.getOperationStart(), requestDto.getOperationEnd(),
                requestDto.getProgramSession(), requestDto.getRecruitNum(),
                requestDto.getStatus(), requestDto.getViewCnt());
        program.setCounselor(counselor);
        return programRepository.save(program).toDto();
    }

    // 집단상담 삭제
    @Transactional
    public void deleteProgram(Long programNo) {
        Program program = programRepository.findById(programNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다." + programNo));

        programRepository.delete(program);
    }

    // 엔티티를 DTO로 변환
    private ProgramResponseDto ProgramDto(Program program) {
        List<ProgramFile> files = programFileRepository.findByProgram(program);
        List<ProgramFileDto> fileDtos = files.stream()
                .map(file -> ProgramFileDto.builder()
                        .fileNo(file.getFileNo())
                        .fileName(file.getFileName())
                        .fileSaveName(file.getFileSaveName())
                        .filePath(file.getFilePath())
                        .fileSize(file.getFileSize())
                        .build())
                .toList();

        ProgramFile thumbnailFile = programFileRepository.findFirstByProgramOrderByFileNoAsc(program);
        ProgramFileDto thumbnailFileDto = thumbnailFile != null ? ProgramFileDto.builder()
                .fileNo(thumbnailFile.getFileNo())
                .fileName(thumbnailFile.getFileName())
                .fileSaveName(thumbnailFile.getFileSaveName())
                .filePath(thumbnailFile.getFilePath())
                .fileSize(thumbnailFile.getFileSize())
                .build() : null;

        return ProgramResponseDto.builder()
                .programNo(program.getProgramNo())
                .counselor(program.getCounselor())
                .programName(program.getProgramName())
                .programContent(program.getProgramContent())
                .recruitStart(program.getRecruitStart())
                .recruitEnd(program.getRecruitEnd())
                .operationStart(program.getOperationStart())
                .operationEnd(program.getOperationEnd())
                .programSession(program.getProgramSession())
                .recruitNum(program.getRecruitNum())
                .status(program.getStatus())
                .viewCnt(program.getViewCnt())
                .thumbnailFile(thumbnailFileDto)
                .files(fileDtos)
                .build();
    }

}
