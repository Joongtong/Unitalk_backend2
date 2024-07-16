package com.unitalk.emp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//지도교수 배정을 위한 학생 리스트 조회 아이템
public class StudentListItem {
    
    private Long studentNo; //학생 일련번호
    private Long studentId; //학생 ID
    private String deptId; //부서코드(학과)
    private String studentName; //학생 이름
    private String studentEmail; //학생 이메일
    private String studentPhoneNumber; //학생 전화번호
    private Long grade; //학년
    private String professorName; //학생 지도교수

}
