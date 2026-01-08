package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    private String title;
    private String description;
    @NotEmpty(message = "Quiz must have at least one question and question list can not be null or empty.")
    private List<CreateQuestionDto> questions;
}
