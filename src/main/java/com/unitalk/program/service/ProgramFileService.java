package com.unitalk.program.service;

import com.unitalk.program.model.entity.Program;
import com.unitalk.program.model.entity.ProgramFile;
import com.unitalk.program.repository.ProgramFileRepository;
import com.unitalk.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramFileService {

    private final ProgramFileRepository programFileRepository;
    private final ProgramRepository programRepository;

    private static final String uploadDir = System.getProperty("user.home") + "/unitalk/uploads";
    private static final String fileBaseUrl = "/uploads/program/";

    // 파일 경로가 생성
    private void createDirectory(String pathStr) {
        Path path = Paths.get(pathStr);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 디렉토리 생성 실패", e);
            }
        }
    }

    @Transactional
    public void saveFiles(List<MultipartFile> files, Long programNo) throws IOException {
        String filePath = "/program/" + programNo + "/";
        createDirectory(uploadDir + filePath);

        // Program 객체 조회
        Program program = programRepository.findById(programNo)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename(); // 파일 원본명
            String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ""; // 파일 확장자
            String fileSaveName = UUID.randomUUID().toString() + fileExtension; // 파일 저장명
            long fileSize = file.getSize(); // 파일 사이즈

            // 파일 저장
            Path path = Paths.get(uploadDir + filePath + fileSaveName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            String fileUrl = fileBaseUrl + programNo + "/" + fileSaveName;

            ProgramFile programFile = ProgramFile.builder()
                    .fileName(originalFileName)
                    .fileSaveName(fileSaveName)
                    .filePath(fileUrl)
                    .fileSize(fileSize)
                    .program(program) // Program 객체 설정
                    .build();
            programFileRepository.save(programFile);
        }
    }

    // 경로확인
    public void printUploadDir() {
        System.out.println("Upload Directory: " + uploadDir);
    }
}
