package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
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
public class QuizQuestionDto {
    private Integer questionId;
    @NotBlank(message = "Question text is mandatory")
    @Size(max = 255, message = "Text length cannot be more than 255")
    private String questionText;
    private List<QuizOptionDto> options;
}
