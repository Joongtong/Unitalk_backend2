package com.unitalk.emp.repository;

import com.unitalk.common.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorListRepository extends JpaRepository<Employee, Long> {

    // "교수" 부서에 속하는 직원들을 deptDetail로 조회합니다.
    Page<Employee> findByDeptDetail(String deptDetail, Pageable pageable);

    // 학과별 교수목록 조회 (페이징 처리)
    Page<Employee> findByDeptDetailAndUser_Department_DeptName(String deptDetail, String deptName, Pageable pageable);

}