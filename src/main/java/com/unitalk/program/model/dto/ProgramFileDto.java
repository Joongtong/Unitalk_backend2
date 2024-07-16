package com.unitalk.program.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProgramFileDto {
    private Long fileNo;
    private String fileName;
    private String fileSaveName;
    private String filePath;
    private Long fileSize;

    public ProgramFileDto(Long fileNo, String fileName, String fileSaveName, String filePath, Long fileSize) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
