package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizCorrectAnswerDto {
    private Integer questionId;
    private Integer optionId;
    @NotBlank(message = "Answer text is mandatory")
    @Size(max = 255, message = "Text length cannot be more than 255")
    private String answer;
}
