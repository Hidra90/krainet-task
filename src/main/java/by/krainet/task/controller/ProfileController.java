package by.krainet.task.controller;

import by.krainet.task.model.Person;
import by.krainet.task.model.dto.ProfileUpdateDto;
import by.krainet.task.model.dto.mapper.PersonMapper;
import by.krainet.task.service.AuthenticationService;
import by.krainet.task.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final PersonService personService;
    private final AuthenticationService authenticationService;
    private final PersonMapper personMapper;

    @GetMapping
    public String profile(Model model) {
        Long currentUserId = authenticationService.getCurrentUserId();
        Person person = personService.getById(currentUserId);
        ProfileUpdateDto dto = personMapper.toDto(person);
        model.addAttribute("person", person);
        model.addAttribute("personDto", dto);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute ProfileUpdateDto updateDto) {
        Long currentUserId = authenticationService.getCurrentUserId();
        personService.updateProfile(currentUserId, updateDto);
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteProfile() {
        Long currentUserId = authenticationService.getCurrentUserId();
        personService.delete(personService.getById(currentUserId));
        return "redirect:/login?deleted";
    }
}
