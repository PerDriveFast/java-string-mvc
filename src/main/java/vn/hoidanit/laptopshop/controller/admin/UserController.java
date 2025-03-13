package vn.hoidanit.laptopshop.controller.admin;

import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    // private final UserRepository userRepository;
    // source action -> generate contractor

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        // this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsers();
        System.out.println(arrUsers);
        model.addAttribute("trung", "test");
        model.addAttribute("trungdanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUsersById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUsersById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String PostUpdateUser(Model model,
            @Valid @ModelAttribute("newUser") User user, // Thêm @Valid trước User
            BindingResult bindingResultUser,
            @RequestParam(value = "hoidanitFile", required = false) MultipartFile file) {

        User currentUser = this.userService.getUsersById(user.getId());

        if (bindingResultUser.hasErrors()) {
            // Giữ lại ảnh cũ khi có lỗi
            user.setAvatar(currentUser.getAvatar());
            model.addAttribute("newUser", user);
            return "admin/user/update"; // Trả về trang cập nhật để sửa lỗi
        }

        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setEmail(user.getEmail());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());

            // Nếu có file mới, cập nhật avatar
            if (file != null && !file.isEmpty()) {
                String avatarPath = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatarPath);
            }

            // Cập nhật Role
            if (user.getRole() != null && user.getRole().getName() != null) {
                Role role = this.userService.getRoleByname(user.getRole().getName());
                if (role != null) {
                    currentUser.setRole(role);
                }
            }

            this.userService.handleSaveUser(currentUser);
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/create")
    public String get(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create1")
    public String CreateUserPage(Model model, @ModelAttribute("newUser") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if (newUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }
        //
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setAvatar(avatar);
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByname(user.getRole().getName()));
        // save
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("newUser") User user) {
        this.userService.deleteUser(user.getId());
        return "redirect:/admin/user"; // Đúng đường dẫn sau khi xóa
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
