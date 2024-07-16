package com.unitalk.emp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//지도교수 배정을 위한 지도교수 리스트 조회 아이템
public class ProfessorListItem {
    
    private Long employeeNo; //교직원 일련번호
    private Long employeeId; //교직원 ID
    private String deptId; //부서코드(학과)
    private String employeeName; //교직원 이름
    private String employeeEmail; //교직원 이메일
    private String employeePhoneNumber; //교직원 전화번호

}
