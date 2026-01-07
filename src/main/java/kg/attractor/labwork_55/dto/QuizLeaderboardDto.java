package kg.attractor.labwork_55.dto;

import lombok.*;

import java.util.LinkedHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizLeaderboardDto {
    private LinkedHashMap<String, Integer> leaderboard;
}