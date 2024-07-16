package com.unitalk.counseling.model.dto.response;

import com.unitalk.common.model.entity.Employee;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorScheduleResponseDto {

    private Long schNo;
    private Employee counselor;
    private String days;
    private Long availTime;
    private Long status;

}
