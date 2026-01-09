package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewQuizGeneralDto {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Text length cannot be more than 50")
    private String title;
    @NotNull(message = "Should not be null")
    @Positive(message = "Must be positive digit")
    private int questionCount;
}
