package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewQuizDetailedDto {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Text length cannot be more than 50")
    private String title;
    private List<QuizQuestionDto> questions;
}
