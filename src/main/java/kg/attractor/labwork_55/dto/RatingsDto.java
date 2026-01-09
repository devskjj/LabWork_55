package kg.attractor.labwork_55.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingsDto {
    private List<UserRatingDto> ratings;
}
