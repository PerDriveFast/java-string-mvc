package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// public class UserController {

// @GetMapping("/")

// public String getHomePage() {

// return "Hello from Controller";
// }
// }

@RestController
public class UserController {

    private UserService userService;

    // source action -> generate contractor

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getHomePage() {
        return this.userService.handleHello();
    }

}
