package kg.attractor.labwork_55.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {
    private Integer id;
    private Integer userId;
    private Integer quizId;
    private Integer score;
}
