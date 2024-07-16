package com.unitalk.counseling.repository;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.counseling.model.entity.Counseling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounselingRepository extends JpaRepository<Counseling, Long> {
    Page<Counseling> findByCounselorOrderByApplicationDateDesc(Employee counselor, Pageable pageable);

    List<Counseling> findByStudent_StudentNo(Long studentNo);

    @Query("SELECT c FROM Counseling c WHERE c.student.studentNo = :studentNo " +
            "AND (:counselMode IS NULL OR c.counselMode = :counselMode) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:counselType IS NULL OR c.department.deptId = :counselType) " +
            "AND (:startDate IS NULL OR c.applicationDate >= :startDate) " +
            "AND (:endDate IS NULL OR c.applicationDate <= :endDate)")
    Page<Counseling> findByFilters(
            @Param("studentNo") Long studentNo,
            @Param("counselMode") Long counselMode,
            @Param("status") Long status,
            @Param("counselType") String counselType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT c FROM Counseling c " +
            "WHERE c.counselor.employeeNo = :counselorNo " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:hasResult IS NULL OR " +
            "    (:hasResult = true AND c.counselContent IS NOT NULL) OR " +
            "    (:hasResult = false AND c.counselContent IS NULL)) " +
            "AND (LOWER(c.student.user.userName) LIKE LOWER(CONCAT('%', :searchQuery, '%')) " +
            "     OR CAST(c.student.user.userId AS string) LIKE CONCAT('%', :searchQuery, '%'))")
    Page<Counseling> findFilteredCounselings(
            @Param("counselorNo") Long counselorNo,
            @Param("status") Long status,
            @Param("hasResult") Boolean hasResult,
            @Param("searchQuery") String searchQuery,
            Pageable pageable
    );

}
