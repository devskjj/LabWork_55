package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizCorrectAnswerDto;
import kg.attractor.labwork_55.dto.QuizResultsDto;
import kg.attractor.labwork_55.dto.UserAnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizResultsDto getQuizResults(Integer quizId, Authentication auth) {
        String email = auth.getName();
        Integer userId = jdbcTemplate.queryForObject(
                "SELECT id FROM users WHERE email = ?",
                Integer.class,
                email
        );
        
        String sqlUserAnswers = "SELECT ua.question_id, ua.option_id " +
                "FROM user_answers ua " +
                "WHERE ua.quiz_id = ? AND ua.user_id = ?";

        List<UserAnswerDto> userAnswers = jdbcTemplate.query(sqlUserAnswers, (rs, rowNum) ->
                        new UserAnswerDto(rs.getInt("question_id"), rs.getInt("option_id")),
                quizId, userId
        );

        String sql = "SELECT o.option_text, q.id AS question_id\n" +
                "FROM options o\n" +
                "JOIN questions q ON o.question_id = q.id\n" +
                "JOIN quizzes qz ON qz.id = q.quiz_id\n" +
                "WHERE qz.id = ?\n" +
                "AND o.is_correct = true";

        List<QuizCorrectAnswerDto> correctAnswers = jdbcTemplate.query(sql, (rs, rowNum) ->
                QuizCorrectAnswerDto.builder()
                        .questionId(rs.getInt("question_id"))
                        .answer(rs.getString("option_text"))
                        .build(), quizId);

        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_answers ua JOIN options o ON ua.option_id = o.id WHERE ua.quiz_id = ? AND ua.user_id = ? AND o.is_correct = true",
                Integer.class,
                quizId, userId
        );

        String totalQuestions = "SELECT COUNT(*) FROM questions WHERE quiz_id = ?";
        Integer totalCorrectAnswers = jdbcTemplate.queryForObject(totalQuestions, Integer.class, quizId);
        String result = count + "/" + totalCorrectAnswers;

        return QuizResultsDto.builder()
                .correctAnswers(correctAnswers)
                .result(result)
                .build();
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
