package com.unitalk.program.service;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.Student;
import com.unitalk.common.repository.EmployeeRepository;
import com.unitalk.common.repository.StudentRepository;
import com.unitalk.program.model.dto.request.ProgramApplicantRequestDto;
import com.unitalk.program.model.dto.response.ProgramApplicantResponseDto;
import com.unitalk.program.model.entity.Program;
import com.unitalk.program.model.entity.ProgramApplicant;
import com.unitalk.program.repository.ProgramApplicantRepository;
import com.unitalk.program.repository.ProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProgramApplicantService {

    private final ProgramApplicantRepository programApplicantRepository;
    private final ProgramRepository programRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

    // 학생의 집단상담 신청 조회
    @Transactional(readOnly = true)
    public Page<ProgramApplicantResponseDto> getStudentApplications(Long studentNo, Pageable pageable) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 정보가 없습니다." + studentNo));
        return programApplicantRepository.findByStudentOrderByApplicantDateDesc(student, pageable)
                .map(this::ProgramDto);
    }

    // 학생의 집단상담 신청 작성
    @Transactional
    public ProgramApplicantResponseDto createApplication(ProgramApplicantRequestDto requestDto) {
        Program program = programRepository.findById(requestDto.getProgramNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." + requestDto.getProgramNo()));
        Student student = studentRepository.findById(requestDto.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 정보가 없습니다." + requestDto.getStudentNo()));

        ProgramApplicant application = ProgramApplicant.builder()
                .program(program)
                .student(student)
                .applicantDate(requestDto.getApplicantDate())
                .status(requestDto.getStatus())
                .build();

        ProgramApplicant savedApplication = programApplicantRepository.save(application);
        return ProgramDto(savedApplication);
    }

    // 전교생 집단상담 신청 목록 조회(교직원, 상담사)
    public Page<ProgramApplicantResponseDto> getAllApplicants(Pageable pageable) {
        Page<ProgramApplicant> applicants = programApplicantRepository.findAllByOrderByApplicantDateDesc(pageable);
        return applicants.map(this::ProgramDto);
    }

    // 집단상담 필터 및 검색
    public Page<ProgramApplicantResponseDto> getProgramApplicantByFilters(Long programNo, Long studentNo,
                                                                          LocalDate applicantDate, Long status, Pageable pageable) {
        Page<ProgramApplicant> applicants = programApplicantRepository.findByFilters(programNo, studentNo, applicantDate, status, pageable);
        return applicants.map(this::ProgramDto);
    }

    // 특정 학생의 집단상담 신청 작성(교직원, 상담사)
    @Transactional
    public ProgramApplicantResponseDto createApplicationForStudent(ProgramApplicantRequestDto requestDto, Long employeeNo) {
        // 학생 존재 여부 확인
        Student student = studentRepository.findById(requestDto.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 정보가 없습니다." + requestDto.getStudentNo()));
        // 프로그램 존재 여부 확인
        Program program = programRepository.findById(requestDto.getProgramNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." + requestDto.getProgramNo()));
        // 교직원 정보 검증
        Employee employee = employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 교직원 정보가 없습니다." + employeeNo));

        // 신청 객체 생성
        ProgramApplicant application = new ProgramApplicant();
        application.setProgram(program);
        application.setStudent(student);
        application.setStatus(requestDto.getStatus());
        application.setApplicantDate(requestDto.getApplicantDate());

        // 신청 저장
        ProgramApplicant savedApplication = programApplicantRepository.save(application);
        return ProgramDto(savedApplication);
    }

    // 집단상담 신청 수정
    @Transactional
    public ProgramApplicantResponseDto cancelApplication(Long applicantNo, Long employeeNo) {
        // 교직원 정보 검증
        Employee employee = employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 교직원 정보가 없습니다." + employeeNo));
        // 신청내역 검증
        ProgramApplicant application = programApplicantRepository.findById(applicantNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청내역이 없습니다." + applicantNo));
        application.setStatus(2L);

        ProgramApplicant updatedApplication = programApplicantRepository.save(application);
        return ProgramDto(updatedApplication);
    }

    // 엔티티를 DTO로 변환
    private ProgramApplicantResponseDto ProgramDto(ProgramApplicant programApplicant) {
        return ProgramApplicantResponseDto.builder()
                .applicantNo(programApplicant.getApplicantNo())
                .program(programApplicant.getProgram())
                .student(programApplicant.getStudent())
                .applicantDate(programApplicant.getApplicantDate())
                .status(programApplicant.getStatus())
                .build();
    }

}
