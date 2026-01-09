package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.QuizOptionDto;
import kg.attractor.labwork_55.dto.QuizQuestionDto;
import kg.attractor.labwork_55.dto.ViewQuizDetailedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ViewQuizDetailedDao {
    private final JdbcTemplate jdbcTemplate;

    public ViewQuizDetailedDto getQuizDetails(Integer quizId, int page, int size) {
        int offset = page * size;
        String sql = "SELECT id, question_text " +
                "FROM questions " +
                "WHERE quiz_id = ? " +
                "ORDER BY id " +
                "LIMIT ? OFFSET ?";

        List<QuizQuestionDto> questions = jdbcTemplate.query(sql,
                (rs, rowNum) -> QuizQuestionDto.builder()
                        .questionId(rs.getInt("id"))
                        .questionText(rs.getString("question_text"))
                        .options(new ArrayList<>())
                        .build(),
                quizId, size, offset
        );

        if (questions.isEmpty()) {
            String sqlQuery = "SELECT title FROM quizzes WHERE id = ?";
            return ViewQuizDetailedDto.builder()
                    .title(jdbcTemplate.queryForObject(sqlQuery, String.class, quizId))
                    .questions(new ArrayList<>())
                    .page(page)
                    .size(size)
                    .build();
        }

        Map<Integer, List<QuizOptionDto>> optionsMap = getAllAnswersForQuestions(questions);
        for (QuizQuestionDto q : questions) {
            q.setOptions(optionsMap.getOrDefault(q.getQuestionId(), new ArrayList<>()));
        }

        String title = getTitle(quizId);
        return ViewQuizDetailedDto.builder()
                .title(title)
                .questions(questions)
                .page(page)
                .size(size)
                .build();
    }

    private String getTitle(Integer quizId) {
        String title = jdbcTemplate.queryForObject(
                "SELECT title FROM quizzes WHERE id = ?",
                String.class, quizId
        );
        return title;
    }

    private Map<Integer, List<QuizOptionDto>> getAllAnswersForQuestions(List<QuizQuestionDto> questions) {
        List<Integer> questionIds = questions.stream()
                .map(QuizQuestionDto::getQuestionId)
                .toList();

        Map<Integer, List<QuizOptionDto>> optionsMap = jdbcTemplate.query(
                String.format(
                        "SELECT question_id, option_text FROM options WHERE question_id IN (%s)",
                        questionIds.stream().map(String::valueOf).collect(Collectors.joining(","))),
                rs -> {
                    Map<Integer, List<QuizOptionDto>> map = new HashMap<>();
                    while (rs.next()) {
                        int qId = rs.getInt("question_id");
                        map.computeIfAbsent(qId, k -> new ArrayList<>())
                                .add(new QuizOptionDto(rs.getString("option_text")));
                    }
                    return map;
                }
        );
        return optionsMap;
    }

    public boolean quizExists(Integer quizId) {
        String sql = "SELECT COUNT (*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId);
        return count != null && count > 0;
    }
}
