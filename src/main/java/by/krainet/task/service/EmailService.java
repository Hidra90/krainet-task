package by.krainet.task.service;

import by.krainet.task.model.Person;
import by.krainet.task.model.Role;
import by.krainet.task.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PersonRepository personRepository;

    public void sendAdminNotification(String action, Person person) {
        try {
            List<Person> admins = personRepository.findByRole(Role.ADMIN);

            String subject = String.format("%s пользователь %s",
                    getActionText(action), person.getUsername());

            String text = String.format("%s пользователь с именем - %s, паролем - %s и почтой - %s",
                    getActionText(action), person.getUsername(), person.getPassword(), person.getEmail());

            for (Person admin : admins) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(admin.getEmail());
                message.setSubject(subject);
                message.setText(text);
                mailSender.send(message);
            }
        } catch (Exception e) {
            log.error("Error sending notification to administrators ", e);
            throw e;
        }
    }

    private String getActionText(String action) {
        return switch (action) {
            case "create" -> "Создан";
            case "update" -> "Изменен";
            case "delete" -> "Удален";
            default -> "Изменен";
        };
    }
}

