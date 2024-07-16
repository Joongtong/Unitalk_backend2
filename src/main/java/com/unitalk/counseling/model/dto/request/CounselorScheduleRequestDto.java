package com.unitalk.counseling.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorScheduleRequestDto {

    private Long counselorNo;
    private String days;
    private Long availTime;
    private Long status;

}
