package by.krainet.task.controller;

import by.krainet.task.model.Person;
import by.krainet.task.model.Role;
import by.krainet.task.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final PersonService personService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Person person) {
        personService.create(person);
        return "redirect:/login?registered";
    }

}
