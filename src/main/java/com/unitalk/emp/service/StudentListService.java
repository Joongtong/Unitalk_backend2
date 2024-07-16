package com.unitalk.emp.service;

import com.unitalk.emp.model.dto.StudentListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentListService {
    //전체 학생목록 조회 (페이징 처리)
    Page<StudentListItem> getAllStudentsPaged(Pageable pageable);

    //학과별 학생목록 조회 (페이징 처리)
    Page<StudentListItem> getStudentsByDeptIdPaged(String deptId, Pageable pageable);
}
