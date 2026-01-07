package kg.attractor.labwork_55.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatisticsDto {
    private Map<String, Object> statistics;
}
