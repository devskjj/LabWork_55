package kg.attractor.labwork_55.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class QuizTimerDao {
    private final JdbcTemplate jdbcTemplate;

    public void startTimer(Integer quizId, String email) {
        String sql = "INSERT INTO quiz_timer (quiz_id, user_id, started_at) VALUES (?, (SELECT id FROM users WHERE email =?), ?)";
        jdbcTemplate.update(sql, quizId, email, Timestamp.valueOf(LocalDateTime.now()));
    }

    public LocalDateTime endQuizTime(Integer quizId, String email) {
        finishQuiz(quizId, email);
        String sql = "SELECT started_at FROM quiz_timer WHERE quiz_id = ? AND user_id = (SELECT id FROM users WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getTimestamp("started_at").toLocalDateTime(), quizId, email);
    }

    public void finishQuiz(Integer quizId, String email) {
        String sql = "UPDATE quiz_timer SET ended_at = ? WHERE quiz_id = ? AND user_id = (SELECT id FROM users WHERE email = ?)";
        jdbcTemplate.update(sql, Timestamp.valueOf(LocalDateTime.now()), quizId, email);
    }

}
