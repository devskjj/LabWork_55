package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultsDto {
    private List<QuizCorrectAnswerDto> correctAnswers;
    @NotBlank(message = "Must not be null")
    @PositiveOrZero(message = "Must be positive or zero")
    private String result;
}
