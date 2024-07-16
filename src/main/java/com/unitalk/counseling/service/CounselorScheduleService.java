package com.unitalk.counseling.service;

import com.unitalk.counseling.model.dto.request.CounselorScheduleRequestDto;
import com.unitalk.counseling.model.dto.response.CounselorScheduleResponseDto;
import com.unitalk.counseling.model.entity.CounselorSchedule;
import com.unitalk.counseling.repository.CounselorScheduleRepository;
import com.unitalk.common.repository.EmployeeRepository;
import com.unitalk.common.model.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CounselorScheduleService {

    private final CounselorScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CounselorScheduleService(CounselorScheduleRepository scheduleRepository,
                                    EmployeeRepository employeeRepository,
                                    ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    // 상담사의 스케쥴 등록
    @Transactional
    public CounselorScheduleResponseDto registerSchedule(CounselorScheduleRequestDto requestDto) {
        Employee counselor = employeeRepository.findById(requestDto.getCounselorNo())
                .orElseThrow(() -> new RuntimeException("Counselor not found with ID: " + requestDto.getCounselorNo()));

        CounselorSchedule schedule = modelMapper.map(requestDto, CounselorSchedule.class);
        schedule.setCounselor(counselor);

        CounselorSchedule savedSchedule = scheduleRepository.save(schedule);

        return modelMapper.map(savedSchedule, CounselorScheduleResponseDto.class);
    }

    // 상담사ID로 해당 상담사의 모든 스케쥴 조회
    public List<CounselorScheduleResponseDto> getSchedulesByCounselorNo(long counselorNo) {
        Employee counselor = employeeRepository.findById(counselorNo)
                .orElseThrow(() -> new RuntimeException("Counselor not found"));
        List<CounselorSchedule> schedules = scheduleRepository.findByCounselor(counselor);
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, CounselorScheduleResponseDto.class))
                .collect(Collectors.toList());
    }

    // 스케쥴번호로 해당 스케쥴 삭제
    @Transactional
    public void deleteSchedule(Long schNo) {
        scheduleRepository.deleteById(schNo);
    }

    // 스케쥴 정보 업데이트
    @Transactional
    public CounselorScheduleResponseDto updateSchedule(Long schNo, CounselorScheduleRequestDto requestDto) {
        CounselorSchedule existingSchedule = scheduleRepository.findById(schNo)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        modelMapper.map(requestDto, existingSchedule);

        if (!existingSchedule.getCounselor().getEmployeeNo().equals(requestDto.getCounselorNo())) {
            Employee newCounselor = employeeRepository.findById(requestDto.getCounselorNo())
                    .orElseThrow(() -> new RuntimeException("Counselor not found"));
            existingSchedule.setCounselor(newCounselor);
        }

        CounselorSchedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return modelMapper.map(updatedSchedule, CounselorScheduleResponseDto.class);
    }
}