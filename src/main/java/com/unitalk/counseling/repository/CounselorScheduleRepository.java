package com.unitalk.counseling.repository;

import com.unitalk.common.model.entity.Employee;
import com.unitalk.counseling.model.entity.CounselorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselorScheduleRepository extends JpaRepository<CounselorSchedule, Long> {
    List<CounselorSchedule> findByCounselor(Employee counselor);
}
