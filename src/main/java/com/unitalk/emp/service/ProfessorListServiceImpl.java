package com.unitalk.emp.service;

import com.unitalk.common.model.entity.Department;
import com.unitalk.common.model.entity.Employee;
import com.unitalk.common.model.entity.User;
import com.unitalk.common.repository.DepartmentRepository;
import com.unitalk.emp.model.dto.ProfessorListItem;
import com.unitalk.emp.repository.ProfessorListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProfessorListServiceImpl implements ProfessorListService {

    private final ProfessorListRepository professorListRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public ProfessorListServiceImpl(ProfessorListRepository professorListRepository, DepartmentRepository departmentRepository) {
        this.professorListRepository = professorListRepository;
        this.departmentRepository = departmentRepository;
    }

    // 전체 교수목록 조회 (페이징 처리)
    @Override
    public Page<ProfessorListItem> getAllProfessorsPaged(Pageable pageable) {
        Page<Employee> employeesPage = professorListRepository.findByDeptDetail("교수", pageable);
        return employeesPage.map(this::mapEmployeeToProfessorListItem);
    }

    // 학과별 교수목록 조회 (페이징 처리)
    @Override
    public Page<ProfessorListItem> getProfessorsByDeptIdPaged(String deptId, Pageable pageable) {
        Department department = departmentRepository.findByDeptId(deptId);
        if (department == null) {
            throw new IllegalArgumentException("Invalid deptId: " + deptId);
        }

        String deptName = department.getDeptName();
        Page<Employee> employeesPage = professorListRepository.findByDeptDetailAndUser_Department_DeptName("교수", deptName, pageable);
        return employeesPage.map(this::mapEmployeeToProfessorListItem);
    }

    private ProfessorListItem mapEmployeeToProfessorListItem(Employee employee) {
        User professorUser = employee.getUser();
        return new ProfessorListItem(
                employee.getEmployeeNo(),
                professorUser.getUserId(),
                professorUser.getDepartment().getDeptName(),
                professorUser.getUserName(),
                professorUser.getEmail(),
                professorUser.getTel()
        );
    }

}
