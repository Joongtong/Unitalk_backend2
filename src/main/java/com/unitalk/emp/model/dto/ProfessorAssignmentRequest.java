package com.unitalk.emp.model.dto;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.ProfessorAssignment;
import com.unitalk.common.model.entity.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfessorAssignmentRequest {

    private Long professorNo;
    private Long studentNo;

    public ProfessorAssignment toEntity(Employee professor, Student student) {
        return ProfessorAssignment.builder()
                .professorNo(professor)
                .studentNo(student)
                .build();
    }

}
