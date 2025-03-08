package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private UserService userService;

    // source action -> generate contractor

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")

    public String getHomePage() {
        String test = this.userService.handleHello();
        return "hello";
    }
}

// @RestController
// public class UserController {

// // DI: dependency injection
// private UserService userService;

// // source action -> generate contractor

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("")
// public String getHomePage() {
// return this.userService.handleHello();
// }

// }
