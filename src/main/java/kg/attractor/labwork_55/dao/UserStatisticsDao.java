package kg.attractor.labwork_55.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserStatisticsDao {
    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> getStatistics(Integer userId) {
        String sql = "SELECT COUNT(quiz_id) AS total_finished_quizzes, AVG(score) as average_score, SUM(score) as total_score\n" + "FROM quiz_results\n" + "WHERE user_id = ?";

        return jdbcTemplate.queryForMap(sql, userId);
    }

    public boolean userExists(Integer userId) {
        String sql = "SELECT COUNT (*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
}
