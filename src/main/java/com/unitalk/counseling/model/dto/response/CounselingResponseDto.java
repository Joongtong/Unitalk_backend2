package com.unitalk.counseling.model.dto.response;

import com.unitalk.common.model.entity.Department;
import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.Student;
import com.unitalk.counseling.model.entity.CounselorSchedule;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselingResponseDto {

    private Long reqNo;
    private Student student;
    private Employee counselor;
    private LocalDate counselDate;
    private CounselorSchedule schedule;
    private LocalDateTime applicationDate;
    private Long counselMode;
    private Department department;
    private String applicationContent;
    private Long status;
    private String counselContent;
    private LocalDateTime recordTime;

}