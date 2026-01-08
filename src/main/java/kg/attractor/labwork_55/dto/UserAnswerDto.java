package kg.attractor.labwork_55.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswerDto {
    private Integer questionId;
    private Integer optionId;
}
