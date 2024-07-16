package com.unitalk.counseling.repository;

import com.unitalk.common.model.entity.Student;
import com.unitalk.counseling.model.entity.Counseling;
import com.unitalk.counseling.model.entity.CounselingReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselingReviewRepository extends JpaRepository<CounselingReview, Long> {
    List<CounselingReview> findByStudent(Student student);
    List<CounselingReview> findByReqCounseling(Counseling counseling);
}
