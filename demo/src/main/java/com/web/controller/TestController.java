package com.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api2/boards")
public class TestController {

    @GetMapping("/list_a")
	public String a() {
		System.out.println("TestController.a() call!!!");
		return null;
	}
    @PostMapping("/postBoard")
	//@RequestMapping(value="/postBoard" , method = {RequestMethod.GET,RequestMethod.POST})
	public String postBoard() {
		System.out.println("TestController.postBoard() call!!!");
		return "포스트";
	}
}
