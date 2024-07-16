package com.unitalk.program.repository;

import com.unitalk.program.model.entity.Program;
import com.unitalk.program.model.entity.ProgramFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramFileRepository extends JpaRepository<ProgramFile, Long> {

    ProgramFile findFirstByProgramOrderByFileNoAsc(Program program);
    List<ProgramFile> findByProgram(Program program);
}
