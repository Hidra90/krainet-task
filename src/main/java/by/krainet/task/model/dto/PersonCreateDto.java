package by.krainet.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonCreateDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
