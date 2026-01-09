package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswerDto {
    @NotNull(message = "Question Id can not be null")
    @Positive
    private Integer questionId;
    @NotNull(message = "Option Id can not be null")
    @Positive
    private Integer optionId;
}
