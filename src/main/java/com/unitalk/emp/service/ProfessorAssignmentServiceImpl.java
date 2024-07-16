package com.unitalk.emp.service;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.ProfessorAssignment;
import com.unitalk.common.model.entity.Student;
import com.unitalk.common.model.entity.User;
import com.unitalk.common.repository.EmployeeRepository;
import com.unitalk.common.repository.StudentRepository;
import com.unitalk.emp.model.dto.ProfessorAssignmentListItem;
import com.unitalk.emp.model.dto.ProfessorAssignmentRequest;
import com.unitalk.emp.repository.ProfessorAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorAssignmentServiceImpl implements ProfessorAssignmentService {

    private final ProfessorAssignmentRepository professorAssignmentRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ProfessorAssignmentServiceImpl(ProfessorAssignmentRepository professorAssignmentRepository, StudentRepository studentRepository, EmployeeRepository employeeRepository) {
        this.professorAssignmentRepository = professorAssignmentRepository;
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
    }

    //전체 지도교수 배정 이력 조회
    @Override
    public Page<ProfessorAssignmentListItem> getAllAssignmentsPaged(Pageable pageable) {
        return professorAssignmentRepository.findAll(pageable).map(this::mapToassignmentListItem);
    }

    //학과별 지도교수 배정 이력 조회
    @Override
    public Page<ProfessorAssignmentListItem> getAssignmentsByDeptIdPaged(String deptId, Pageable pageable) {
        return professorAssignmentRepository.findByStudentNo_User_Department_DeptId(deptId, pageable).map(this::mapToassignmentListItem);
    }

    //모든 배정 이력을 ListItem으로 변환
    private ProfessorAssignmentListItem mapToassignmentListItem(ProfessorAssignment professorAssignment) {
        Student student = professorAssignment.getStudentNo();
        Employee professor = professorAssignment.getProfessorNo();

        student.setUser(student.getUser());
        professor.setUser(professor.getUser());

        User studentUser = student.getUser();
        User professorUser = professor.getUser();

        String studentDeptId = studentUser.getDepartment().getDeptName();
        String professorDeptId = professorUser.getDepartment().getDeptName();

        return new ProfessorAssignmentListItem(
                professorAssignment.getAssignmentNo(),
                professorUser.getUserId(),
                professorDeptId,
                professorUser.getUserName(),
                studentUser.getUserId(),
                studentDeptId,
                studentUser.getUserName(),
                professorAssignment.getAssignmentDate()
        );
    }

    //지도교수 배정 이력 생성
    @Override
    public Long save(ProfessorAssignmentRequest params) {
        Employee professor = employeeRepository.findById(params.getProfessorNo()).orElseThrow(() -> new IllegalArgumentException("Invalid professor ID"));
        Student student = studentRepository.findById(params.getStudentNo()).orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        ProfessorAssignment professorAssignment = params.toEntity(professor, student);
        professorAssignmentRepository.save(professorAssignment);

        student.setProfessorNo(professor);
        studentRepository.save(student);

        return professorAssignment.getAssignmentNo();
    }

}