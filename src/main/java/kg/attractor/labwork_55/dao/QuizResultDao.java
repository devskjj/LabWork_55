package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizCorrectAnswerDto;
import kg.attractor.labwork_55.dto.QuizResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizResultsDto getQuizResults(Integer quizId) {
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

        return QuizResultsDto.builder()
                .correctAnswers(correctAnswers)
                .result("NaN")
                .build();
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
