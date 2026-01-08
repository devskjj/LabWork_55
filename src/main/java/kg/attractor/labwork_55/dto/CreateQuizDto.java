package kg.attractor.labwork_55.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuizDto {
    private String title;
    private String description;
    private List<CreateQuestionDto> questions;
}
