package kg.attractor.labwork_55.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private Integer id;
    private Integer quizId;
    private String questionText;
}
