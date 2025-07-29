package by.krainet.task.service;

import by.krainet.task.model.Person;
import by.krainet.task.model.Role;
import by.krainet.task.model.dto.PersonUpdateDto;
import by.krainet.task.model.dto.ProfileUpdateDto;
import by.krainet.task.model.dto.mapper.PersonMapper;
import by.krainet.task.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonMapper personMapper;
    private final EmailService emailService;

    @Transactional
    public void create(Person person) {
        try {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            personRepository.save(person);
            emailService.sendAdminNotification("create", person);
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw e;
        }
    }

    @Transactional
    public void update(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(PersonUpdateDto updateDto) {
        Person person = loadPersonOrThrow(updateDto.getId());
        personMapper.updateFromDto(updateDto, person);
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            person.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        personRepository.save(person);
    }

    @Transactional
    public void updateProfile(Long userId, ProfileUpdateDto updateDto) {
        try {
            Person person = loadPersonOrThrow(userId);
            personMapper.updatePersonFromProfileDto(updateDto, person);

            if (updateDto.getNewPassword() != null && !updateDto.getNewPassword().isEmpty()) {
                person.setPassword(passwordEncoder.encode(updateDto.getNewPassword()));
            }
            personRepository.save(person);
            emailService.sendAdminNotification("update", person);
        } catch (Exception e) {
            log.error("Error updating user profile ID: {}", userId, e);
            throw e;
        }
    }

    @Transactional
    public void delete(Person person) {
        personRepository.delete(person);
        emailService.sendAdminNotification("delete", person);
    }

    @Transactional(readOnly = true)
    public Person getById(Long id) {
        return loadPersonOrThrow(id);
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Person> getByRole(Role role) {
        return personRepository.findByRole(role);
    }

    private Person loadPersonOrThrow(Long id) {
        return personRepository.findById(id).orElseThrow();
    }
}
