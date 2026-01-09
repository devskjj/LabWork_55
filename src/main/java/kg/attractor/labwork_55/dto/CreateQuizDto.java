package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuizDto {
    @NotBlank(message = "Quiz title can not be blank.")
    @Size(max = 50, message = "Text length cannot be more than 50")
    private String title;
    @NotBlank(message = "Description text is mandatory")
    @Size(max = 255, message = "Description length cannot be more than 255")
    private String description;
    @NotEmpty(message = "Quiz must have at least one question and question list can not be null or empty.")
    private List<CreateQuestionDto> questions;
}
