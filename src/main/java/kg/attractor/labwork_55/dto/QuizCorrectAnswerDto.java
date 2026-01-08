package kg.attractor.labwork_55.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizCorrectAnswerDto {
    private Integer questionId;
    private Integer optionId;
    private String answer;
}
