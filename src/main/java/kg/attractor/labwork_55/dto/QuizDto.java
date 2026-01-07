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
public class QuizDto {
    private Integer id;
    private String title;
    private String description;
    private Integer creatorId;
    private List<QuestionDto> questions;
}
