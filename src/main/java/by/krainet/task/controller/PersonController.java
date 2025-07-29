package by.krainet.task.controller;

import by.krainet.task.model.Person;
import by.krainet.task.model.Role;
import by.krainet.task.model.dto.PersonUpdateDto;
import by.krainet.task.model.dto.mapper.PersonMapper;
import by.krainet.task.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @ModelAttribute("roles")
    public Role[] getRoles() {
        return Role.values();
    }

    @GetMapping
    public String personManagement(Model model) {
        model.addAttribute("persons", personService.getAll());
        return "persons";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Person person = personService.getById(id);
        model.addAttribute("personDto", personMapper.toUpdateDto(person));
        return "person-edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("personDto") PersonUpdateDto updateDto) {
        personService.update(updateDto);
        return "redirect:/persons";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        personService.delete(personService.getById(id));
        return "redirect:/persons";
    }

}
