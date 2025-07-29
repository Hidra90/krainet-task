package by.krainet.task.model.dto;

import by.krainet.task.model.Role;
import lombok.Data;

@Data
public class PersonUpdateDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String password;
}
