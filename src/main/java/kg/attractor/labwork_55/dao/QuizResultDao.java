package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizCorrectAnswerDto;
import kg.attractor.labwork_55.dto.QuizResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public QuizResultDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public QuizResultsDto getQuizResults(Integer quizId, String email) {
        int score = getUserScore(quizId, email);

        Integer totalQuestions = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM questions WHERE quiz_id = ?",
                Integer.class,
                quizId
        );

        String result = score + "/" + totalQuestions;
        return QuizResultsDto.builder()
                .correctAnswers(getCorrectUserAnswers(quizId, email))
                .result(result)
                .build();
    }

    public List<QuizCorrectAnswerDto> getCorrectUserAnswers(Integer quizId, String email) {
        String sql = """
                    SELECT q.id AS question_id, o.id AS option_id, o.option_text
                    FROM user_answers ua
                    JOIN options o ON ua.option_id = o.id
                    JOIN questions q ON o.question_id = q.id
                    WHERE ua.user_id = (SELECT id FROM users WHERE email = ?)
                      AND q.quiz_id = ? AND o.is_correct = true
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        QuizCorrectAnswerDto.builder()
                                .questionId(rs.getInt("question_id"))
                                .optionId(rs.getInt("option_id"))
                                .answer(rs.getString("option_text"))
                                .build(),
                email, quizId
        );
    }

    public int getUserScore(Integer quizId, String email) {
        Integer userId = jdbcTemplate.queryForObject(
                "SELECT id FROM users WHERE email = ?",
                Integer.class,
                email
        );

        if (userId == null) {
            return 0;
        }

        String sql = """
                SELECT COUNT(*)
                FROM user_answers ua
                JOIN options o ON ua.option_id = o.id
                JOIN questions q ON o.question_id = q.id
                WHERE ua.user_id = ? AND q.quiz_id = ? AND o.is_correct = true
                """;

        Integer score = jdbcTemplate.queryForObject(sql, Integer.class, userId, quizId);
        return score != null ? score : 0;
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }

    public void submitScore(MapSqlParameterSource params) {
        String sql = "INSERT INTO quiz_results (user_id, quiz_id, score) VALUES (:userId, :quizId, :score)";
        parameterJdbcTemplate.update(sql, params);
    }
}
