package com.user.signin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author yyhu3
 * @Date 2018-04-24 14:53
 */

//test git branch
@RestController
public class LoginController {
    @GetMapping("/HelloWorld")
    public String HelloWorld()
    {
        return "HelloWorld";
    }
}
