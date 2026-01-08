package kg.attractor.labwork_55.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
public class QuizLeaderboardDao {
    private final JdbcTemplate jdbcTemplate;

    public LinkedHashMap<String, Integer> getLeaderboard(Integer quizId) {
        String sql = "SELECT u.email, q.score\n" +
                "FROM users u\n" +
                "         JOIN quiz_results q ON u.id = q.user_id\n" +
                "WHERE q.quiz_id = ?\n" +
                "ORDER BY q.score DESC";

        LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();

        jdbcTemplate.query(sql, rs -> {
            leaderboard.put(rs.getString("email"), rs.getInt("score"));
        }, quizId);

        return leaderboard;
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT (*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
