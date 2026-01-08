package kg.attractor.labwork_55.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {
    private Integer id;
    @NotNull(message = "User id is mandatory")
    @Positive(message = "User id should be positive digit")
    private Integer userId;
    @NotNull(message = "Quiz id is mandatory")
    @Positive(message = "Quiz id should be positive digit")
    private Integer quizId;
    @NotNull(message = "Score is mandatory")
    @Positive(message = "Score should be positive digit")
    private Integer score;
    @NotNull(message = "Rate is mandatory")
    @Min(value = 1, message = "Cannot be lower than 1")
    @Max(value = 5, message = "Cannot be more than 5")
    private Integer quizRate;
}
