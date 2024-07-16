package com.unitalk.counseling.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselingCountsDto {

    private int professorCounseling;
    private int personalCounseling;
    private int sexualHarassmentCounseling;
    private int studentWelfareCounseling;

}
