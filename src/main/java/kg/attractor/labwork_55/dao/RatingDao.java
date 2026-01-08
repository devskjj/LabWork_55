package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.UserRatingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RatingDao {
    private final JdbcTemplate jdbcTemplate;

    public List<UserRatingDto> getTopTenRatings() {
        String sql = "SELECT u.username, u.email, t.score FROM users u JOIN quiz_results qr ON u.id = qr.user_id JOIN top_scores t ON t.result_id = qr.id ORDER BY t.score DESC LIMIT 10";

        return jdbcTemplate.query(sql, (rs, rowNum) -> UserRatingDto.builder()
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .score(rs.getInt("score"))
                .build());
    }
}
