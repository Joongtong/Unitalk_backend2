package com.unitalk.emp.service;

import com.unitalk.emp.model.dto.ProfessorAssignmentListItem;
import com.unitalk.emp.model.dto.ProfessorAssignmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfessorAssignmentService {

    //전체 지도교수 배정 이력 조회 (페이징 처리)
    Page<ProfessorAssignmentListItem> getAllAssignmentsPaged(Pageable pageable);

    //학과별 지도교수 배정 이력 조회 (페이징 처리)
    Page<ProfessorAssignmentListItem> getAssignmentsByDeptIdPaged(String deptId, Pageable pageable);

    //지도교수 배정 이력 생성
    Long save(ProfessorAssignmentRequest params);

}
