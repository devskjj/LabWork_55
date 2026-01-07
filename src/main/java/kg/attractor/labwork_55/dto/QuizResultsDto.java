package kg.attractor.labwork_55.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultsDto {
    private List<QuizCorrectAnswerDto> correctAnswers;
    private String result;
}
