package kg.attractor.labwork_55.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOptionDto {
    private String optionText;
    private Boolean isCorrect;
}
