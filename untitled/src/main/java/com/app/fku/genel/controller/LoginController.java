package com.app.fku.genel.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/user")
    public String loginUser(){
        return "User kullanıcı girişi başarılı";
    }

    @RequestMapping("/admin")
    public String loginAdmin(){
        return "Admin kullanıcı girişi başarılı";
    }
}