package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRatingDto {
    @NotBlank(message = "Password must not be blank.")
    @Size(max = 50, message = "Max length is 50")
    private String username;
    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Email must match email standards.")
    @Size(max = 150, message = "Max length is 150")
    private String email;
    @NotNull(message = "Score is mandatory")
    @PositiveOrZero(message = "Score should be positive digit or zero")
    private Integer score;
}
