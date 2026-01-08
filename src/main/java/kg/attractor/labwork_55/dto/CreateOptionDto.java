package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOptionDto {
    @NotBlank (message = "Option text can not be blank.")
    @Size(max = 255, message = "Text length cannot be more than 255")
    private String optionText;
    @NotNull (message = "Status if option is correct must be true or false and not null.")
    private Boolean isCorrect;
}
