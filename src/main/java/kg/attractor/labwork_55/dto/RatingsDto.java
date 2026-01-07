package kg.attractor.labwork_55.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingsDto {
    private Map<String, Object> ratings;
}
