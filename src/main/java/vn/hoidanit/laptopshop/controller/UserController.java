package vn.hoidanit.laptopshop.controller;

import vn.hoidanit.laptopshop.service.UserService;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UserService userService;
    // private final UserRepository userRepository;
    // source action -> generate contractor

    public UserController(UserService userService) {
        this.userService = userService;
        // this.userRepository = userRepository;
    }

    @RequestMapping("/client")
    public String getHomePage(Model model) {
        model.addAttribute("trung", "test");
        model.addAttribute("trungdanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String CreateUserPage(Model model, @ModelAttribute("newUser") User user) {
        System.out.println("run here" + user);
        this.userService.handleSaveUser(user);
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
