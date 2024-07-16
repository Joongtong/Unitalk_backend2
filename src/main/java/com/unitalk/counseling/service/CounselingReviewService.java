package com.unitalk.counseling.service;

import com.unitalk.counseling.model.dto.request.CounselingReviewRequestDto;
import com.unitalk.counseling.model.dto.response.CounselingReviewResponseDto;
import com.unitalk.counseling.model.entity.CounselingReview;
import com.unitalk.counseling.model.entity.Counseling;
import com.unitalk.counseling.repository.CounselingReviewRepository;
import com.unitalk.counseling.repository.CounselingRepository;
import com.unitalk.common.repository.StudentRepository;
import com.unitalk.common.model.entity.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CounselingReviewService {

    private final CounselingReviewRepository reviewRepository;
    private final CounselingRepository counselingRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CounselingReviewService(CounselingReviewRepository reviewRepository,
                                   CounselingRepository counselingRepository,
                                   StudentRepository studentRepository,
                                   ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.counselingRepository = counselingRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    // 상담 후기 생성
    @Transactional
    public CounselingReviewResponseDto createReview(CounselingReviewRequestDto requestDto) {
        Counseling counseling = counselingRepository.findById(requestDto.getReqNo())
                .orElseThrow(() -> new RuntimeException("Counseling not found"));
        Student student = studentRepository.findById(requestDto.getStudentNo())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CounselingReview review = modelMapper.map(requestDto, CounselingReview.class);
        review.setReqCounseling(counseling);
        review.setStudent(student);

        CounselingReview savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, CounselingReviewResponseDto.class);
    }

    // 상담 후기 개별 조회
    public CounselingReviewResponseDto getReviewById(Long reviewNo) {
        CounselingReview review = reviewRepository.findById(reviewNo)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return modelMapper.map(review, CounselingReviewResponseDto.class);
    }

    // 학생ID로 해당 학생의 모든 상담 후기 조회
    public List<CounselingReviewResponseDto> getReviewsByStudentNo(Long studentNo) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<CounselingReview> reviews = reviewRepository.findByStudent(student);
        return reviews.stream()
                .map(review -> modelMapper.map(review, CounselingReviewResponseDto.class))
                .collect(Collectors.toList());
    }

    // 상담 신청 번호로 해당 상담의 모든 리뷰 조회
    public List<CounselingReviewResponseDto> getReviewsByCounselingNo(Long reqNo) {
        Counseling counseling = counselingRepository.findById(reqNo)
                .orElseThrow(() -> new RuntimeException("Counseling not found"));
        List<CounselingReview> reviews = reviewRepository.findByReqCounseling(counseling);
        return reviews.stream()
                .map(review -> modelMapper.map(review, CounselingReviewResponseDto.class))
                .collect(Collectors.toList());
    }
}