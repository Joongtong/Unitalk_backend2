package com.unitalk.program.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitalk.common.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "Program_File")
@AllArgsConstructor
@NoArgsConstructor
public class ProgramFile {
    @Id
    @Column(name = "file_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 파일 번호
    private Long fileNo;

    @ManyToOne
    @JoinColumn(name = "program_no", nullable = false) // 집단상담 번호
    @JsonIgnore
    private Program program;

    @Column(name = "file_name", nullable = false) // 파일 원본명
    private String fileName;

    @Column(name = "file_saveName", nullable = false) // 파일 저장명
    private String fileSaveName;

    @Column(name = "file_path", nullable = false) // 파일 경로
    private String filePath;

    @Column(name = "file_size", nullable = false) // 파일 크기
    private Long fileSize;
}
