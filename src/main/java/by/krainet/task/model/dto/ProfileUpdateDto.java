package by.krainet.task.model.dto;

import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String email;
    private String firstName;
    private String lastName;
    private String newPassword;
}
