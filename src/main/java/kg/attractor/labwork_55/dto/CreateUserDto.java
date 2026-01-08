package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    @NotBlank(message = "Username can not be blank.")
    private String username;
    @NotBlank(message = "Password must not be blank.")
    @Size(min = 5, max = 24, message = "Password length must be between 5 and 24 characters.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$", message = "Password must contain at least one digit and one uppercase and lowercase letters.")
    private String password;
    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Email must match email standards.")
    private String email;
}
