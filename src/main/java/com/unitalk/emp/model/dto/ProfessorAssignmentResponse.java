package com.unitalk.emp.model.dto;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.ProfessorAssignment;
import com.unitalk.common.model.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProfessorAssignmentResponse {

    private Long assignmentNo;
    private Employee professorNo;
    private Student studentNo;
    private LocalDateTime assignmentDate;

    public ProfessorAssignmentResponse(ProfessorAssignment entity) {
        this.assignmentNo = entity.getAssignmentNo();
        this.professorNo = entity.getProfessorNo();
        this.studentNo = entity.getStudentNo();
        this.assignmentDate = entity.getAssignmentDate();
    }

}
