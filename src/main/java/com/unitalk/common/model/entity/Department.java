package com.unitalk.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Departments")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "deptId")
public class Department {

    @Id
    @Column(name = "dept_id")   // 부서코드
    private String deptId;

    @Column(name = "dept_name", nullable = false) // 부서명
    private String deptName;

    @ManyToOne
    @JoinColumn(name = "up_dept_id")    // 상위부서코드
    private Department upDeptId;

}