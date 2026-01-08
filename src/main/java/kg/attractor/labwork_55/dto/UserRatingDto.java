package kg.attractor.labwork_55.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRatingDto {
    private String username;
    private String email;
    private Integer score;
}
