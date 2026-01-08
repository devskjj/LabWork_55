package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizCorrectAnswerDto;
import kg.attractor.labwork_55.dto.QuizResultsDto;
import kg.attractor.labwork_55.dto.UserAnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizResultsDto getQuizResults(Integer quizId, String email) {
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

        String sqlCorrectAnswers = "SELECT o.id AS option_id, o.option_text, q.id AS question_id " +
                "FROM options o " +
                "JOIN questions q ON o.question_id = q.id " +
                "WHERE q.quiz_id = ? AND o.is_correct = true";
        List<QuizCorrectAnswerDto> correctAnswers = jdbcTemplate.query(sqlCorrectAnswers, (rs, rowNum) ->
                        QuizCorrectAnswerDto.builder()
                                .questionId(rs.getInt("question_id"))
                                .optionId(rs.getInt("option_id"))
                                .answer(rs.getString("option_text"))
                                .build(),
                quizId
        );

        List<QuizCorrectAnswerDto> correctUserAnswers = userAnswers.stream()
                .flatMap(ua -> correctAnswers.stream()
                        .filter(ca -> ca.getQuestionId().equals(ua.getQuestionId())
                                && ca.getOptionId().equals(ua.getOptionId())))
                .toList();
        int count = correctUserAnswers.size();
        Integer totalQuestions = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM questions WHERE quiz_id = ?",
                Integer.class,
                quizId
        );

        String result = count + "/" + totalQuestions;
        return QuizResultsDto.builder()
                .correctAnswers(correctUserAnswers)
                .result(result)
                .build();
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
