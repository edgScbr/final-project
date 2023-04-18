package com.applaudo.javatraining.finalproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public Map<String, String> greetings() {
        Map<String, String> map = new HashMap<>();
        map.put("greetings", "hello world");
        return map;
    }
}
