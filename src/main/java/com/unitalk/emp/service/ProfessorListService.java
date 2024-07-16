package com.unitalk.emp.service;

import com.unitalk.emp.model.dto.ProfessorListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfessorListService {

    // 전체 교수목록 조회 (페이징 처리)
    Page<ProfessorListItem> getAllProfessorsPaged(Pageable pageable);

    // 학과별 교수목록 조회 (페이징 처리)
    Page<ProfessorListItem> getProfessorsByDeptIdPaged(String deptId, Pageable pageable);

}