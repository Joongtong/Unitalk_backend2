package com.unitalk.emp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorAssignmentListItem {

    private Long assignmentId; //지도교수 배정 이력 일련번호
    private Long professorId; //교직원 ID(지도교수 ID)
    private String professorDeptId; //교직원 부서코드(지도교수 소속 학과)
    private String professorName; //교직원 이름
    private Long studentId; //학생 ID
    private String studentDeptId; //학생 부서코드(학생 소속 학과)
    private String studentName; //학생 이름
    private LocalDateTime assignmentDate; //지도교수 배정 일시

}