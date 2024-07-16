package com.unitalk.program.repository;

import com.unitalk.common.model.entity.Student;
import com.unitalk.program.model.entity.ProgramApplicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProgramApplicantRepository extends JpaRepository<ProgramApplicant, Long> {
    @Query("SELECT pa FROM ProgramApplicant pa WHERE " +
            "(:programNo IS NULL OR pa.program.programNo = :programNo) " +
            "AND (:studentNo IS NULL OR pa.student.studentNo = :studentNo) " +
            "AND (:applicantDate IS NULL OR pa.applicantDate = :applicantDate) " +
            "AND (:status IS NULL OR pa.status = :status)")

    Page<ProgramApplicant> findByFilters(
            @Param("programNo") Long programNo,
            @Param("studentNo") Long studentNo,
            @Param("applicantDate") LocalDate applicantDate,
            @Param("status") Long status,
            Pageable pageable
    );

    Page<ProgramApplicant> findByStudentOrderByApplicantDateDesc(Student student, Pageable pageable);
    Page<ProgramApplicant> findAllByOrderByApplicantDateDesc(Pageable pageable);
}
