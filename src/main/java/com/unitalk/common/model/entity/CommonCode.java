package com.unitalk.common.model.entity;

import com.unitalk.common.util.CommonCodeId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Common_Codes")
@Getter
@NoArgsConstructor
@IdClass(CommonCodeId.class)
public class CommonCode {

    //코드 대분류
    @Id
    @Column(name = "division", nullable = false)
    private String division;

    //분류 코드, PK
    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    //분류 코드명
    @Column(name = "code_name", nullable = false)
    private String codeName;

    @Builder
    public CommonCode(String division, String code, String codeName) {
        this.division = division;
        this.code = code;
        this.codeName = codeName;
    }
}
