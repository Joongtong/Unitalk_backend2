package com.unitalk.emp.repository;

import com.unitalk.common.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentListRepository extends JpaRepository<Student, Long> {

    //학과별 학생목록 조회
    List<Student> findByUserDeptIdDeptId(String deptId);

}
