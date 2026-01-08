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
public class Option {
    private Integer id;
    @NotNull(message = "Question id is mandatory")
    @Positive(message = "Question id should be positive digit")
    private Integer questionId;
    @NotNull(message = "Answer text is mandatory")
    @Size(max = 255, message = "Text length cannot be more than 255")
    private String optionText;
    @NotNull(message = "Must be true or false")
    private Boolean isCorrect;

}
