package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @GetMapping("/")
	public String index() {
		return "Hello World  Trung @!";
	}
	 @GetMapping("/user")
	public String UserPage() {
		return "Only users can asscess this page.";
	}
	 @GetMapping("/admin")
	public String AdminPage() {
		return "Only admin can asscess this page.";
	}
}
