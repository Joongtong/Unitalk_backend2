package com.unitalk.counseling.model.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselingRequestDto {

    private Long studentNo;
    private Long counselorNo;
    private LocalDate counselDate;
    private Long schNo;
    private Long counselMode;
    private String counselType;
    private String applicationContent;

}