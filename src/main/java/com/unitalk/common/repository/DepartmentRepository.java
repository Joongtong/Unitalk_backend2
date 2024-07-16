package com.unitalk.common.repository;

import com.unitalk.common.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    //학과 코드를 통해 학과 정보를 조회하는 메서드
    Department findByDeptId(String deptId);
}
