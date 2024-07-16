package com.unitalk.emp.repository;

import com.unitalk.common.model.entity.ProfessorAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorAssignmentRepository extends JpaRepository<ProfessorAssignment, Long> {

    // 학과별 지도교수 배정 이력 조회 (페이징 처리)
    Page<ProfessorAssignment> findByStudentNo_User_Department_DeptId(String deptId, Pageable pageable);

}