package com.unitalk.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

//    @GetMapping("/api/")
//    public String mainP() {
//
//        return "Main Controller";
//    }

    @GetMapping("/api/admin")
    public String adminP() {

        return "Admin Controller";
    }
}