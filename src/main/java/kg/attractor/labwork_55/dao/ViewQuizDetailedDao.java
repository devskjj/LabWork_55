package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizOptionDto;
import kg.attractor.labwork_55.dto.QuizQuestionDto;
import kg.attractor.labwork_55.dto.ViewQuizDetailedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ViewQuizDetailedDao {
    private final JdbcTemplate jdbcTemplate;

    public ViewQuizDetailedDto getQuizDetails(Integer quizId) {
        String sql = "SELECT\n" +
                "    quiz.title,\n" +
                "    q.id   AS question_id,\n" +
                "    q.question_text,\n" +
                "    o.option_text\n" +
                "FROM quizzes quiz\n" +
                "         JOIN questions q ON q.quiz_id = quiz.id\n" +
                "         JOIN options o ON o.question_id = q.id\n" +
                "WHERE quiz.id = ?\n" +
                "ORDER BY q.id";

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, QuizQuestionDto> questions = new LinkedHashMap<>();
            String title = null;

            while (rs.next()) {
                if (title == null) {
                    title = rs.getString("title");
                }

                Integer questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String optionText = rs.getString("option_text");

                QuizQuestionDto question = questions.computeIfAbsent(
                        questionId,
                        id -> QuizQuestionDto.builder()
                                .questionId(id)
                                .questionText(questionText)
                                .options(new ArrayList<>())
                                .build()
                );

                question.getOptions().add(
                        QuizOptionDto.builder()
                                .optionText(optionText)
                                .build());
            }
            return ViewQuizDetailedDto.builder()
                    .title(title)
                    .questions(new ArrayList<>(questions.values()))
                    .build();
        }, quizId);
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT (*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
