package kg.attractor.labwork_55.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Integer id;
    @NotNull(message = "Quiz id is mandatory")
    @Positive(message = "Quiz id should be positive digit")
    private Integer quizId;
    @NotNull(message = "Question text is mandatory")
    @Size(max = 255, message = "Text length cannot be more than 255")
    private String questionText;
}
