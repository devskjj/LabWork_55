package kg.attractor.labwork_55.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionDto {
    @NotBlank(message = "Question text can not be blank.")
    private String questionText;
    @NotEmpty(message = "Question must have at least a few options and option list can not be null or empty.")
    private List<CreateOptionDto> options;
}
