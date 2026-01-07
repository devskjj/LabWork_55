package kg.attractor.labwork_55.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {
    private Integer id;
    private Integer questionId;
    private String optionText;
    private Boolean isCorrect;
}
