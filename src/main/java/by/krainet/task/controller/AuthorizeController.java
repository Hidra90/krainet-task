package by.krainet.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizeController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
