package by.krainet.task.controller;

import by.krainet.task.model.Role;
import by.krainet.task.service.AuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
public class HomeController {

    private final AuthenticationService authenticationService;

    public HomeController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        Map<String, String> menuItems = new HashMap<>();
        menuItems.put("profile", "/profile");

        if (authenticationService.isAdmin()) {
            menuItems.put("persons", "/persons");
        }

        model.addAttribute("menuItems", menuItems);
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "home";
    }

}
