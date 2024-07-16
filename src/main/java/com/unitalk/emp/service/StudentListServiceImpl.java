package com.unitalk.emp.service;

import com.unitalk.common.model.entity.Student;
import com.unitalk.common.model.entity.User;
import com.unitalk.emp.model.dto.StudentListItem;
import com.unitalk.emp.repository.StudentListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentListServiceImpl implements StudentListService {

    private final StudentListRepository studentListRepository;

    @Autowired
    public StudentListServiceImpl(StudentListRepository studentListRepository) {
        this.studentListRepository = studentListRepository;
    }

    // 전체 학생목록 조회 (페이징 처리)
    @Override
    public Page<StudentListItem> getAllStudentsPaged(Pageable pageable) {
        Page<Student> studentsPage = studentListRepository.findAll(pageable);
        return studentsPage.map(this::mapStudentToStudentListItem);
    }

    // 학과별 학생목록 조회 (페이징 처리)
    @Override
    public Page<StudentListItem> getStudentsByDeptIdPaged(String deptId, Pageable pageable) {
        Page<Student> studentsPage = studentListRepository.findByUserDepartmentDeptId(deptId, pageable);
        return studentsPage.map(this::mapStudentToStudentListItem);
    }

    private StudentListItem mapStudentToStudentListItem(Student student) {
        User studentUser = student.getUser();
        String studentDeptId = studentUser.getDepartment().getDeptName();
        String professorName = student.getProfessor() != null ? student.getProfessor().getUser().getUserName() : "미배정";

        return new StudentListItem(
                student.getStudentNo(),
                studentUser.getUserId(),
                studentDeptId,
                studentUser.getUserName(),
                studentUser.getEmail(),
                studentUser.getTel(),
                student.getGrade(),
                professorName
        );
    }

}
