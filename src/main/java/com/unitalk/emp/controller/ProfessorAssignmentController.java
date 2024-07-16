package com.unitalk.emp.controller;

import com.unitalk.emp.model.dto.ProfessorAssignmentListItem;
import com.unitalk.emp.model.dto.ProfessorAssignmentRequest;
import com.unitalk.emp.model.dto.ProfessorListItem;
import com.unitalk.emp.model.dto.StudentListItem;
import com.unitalk.emp.service.ProfessorAssignmentService;
import com.unitalk.emp.service.ProfessorListService;
import com.unitalk.emp.service.StudentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assign-prof")
public class ProfessorAssignmentController {

    private final ProfessorAssignmentService professorAssignmentService;
    private final ProfessorListService professorListService;
    private final StudentListService studentListService;

    @Autowired
    public ProfessorAssignmentController(ProfessorAssignmentService professorAssignmentService, ProfessorListService professorListService, StudentListService studentListService) {
        this.professorAssignmentService = professorAssignmentService;
        this.professorListService = professorListService;
        this.studentListService = studentListService;
    }

    //전체 교수목록 조회
    @GetMapping("/list/professors/all")
    public Page<ProfessorListItem> getAllProfessorsPaged(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return professorListService.getAllProfessorsPaged(pageable);
    }

    //학과별 교수목록 조회
    @GetMapping("/list/professors/{deptId}")
    public Page<ProfessorListItem> getProfessorsByDeptIdPaged(@PathVariable String deptId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return professorListService.getProfessorsByDeptIdPaged(deptId, pageable);
    }

    //전체 학생목록 조회
    @GetMapping("/list/students/all")
    public Page<StudentListItem> getStudentsPaged(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentListService.getAllStudentsPaged(pageable);
    }

    //학과별 학생목록 조회
    @GetMapping("/list/students/{deptId}")
    public Page<StudentListItem> getStudentsByDeptIdPaged(@PathVariable String deptId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentListService.getStudentsByDeptIdPaged(deptId, pageable);
    }

    //전체 지도교수 배정 이력 조회
    @GetMapping("/list/assignments/all")
    public Page<ProfessorAssignmentListItem> getAssignmentsPaged(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assignmentNo").descending());
        return professorAssignmentService.getAllAssignmentsPaged(pageable);
    }

    //학과별 지도교수 배정 이력 조회
    @GetMapping("/list/assignments/{deptId}")
    public Page<ProfessorAssignmentListItem> getAssignmentsByDeptIdPaged(@PathVariable String deptId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assignmentNo").descending());
        return professorAssignmentService.getAssignmentsByDeptIdPaged(deptId, pageable);
    }

    //지도교수 배정 이력 생성
    @PostMapping("/new")
    public Long save(@RequestBody final ProfessorAssignmentRequest params) {
        return professorAssignmentService.save(params);
    }

}
