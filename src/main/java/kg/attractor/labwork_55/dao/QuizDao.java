package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.ViewQuizGeneralDto;
import kg.attractor.labwork_55.models.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public QuizDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isAlreadyExists(Integer quizId, String name) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE creator_id = ? AND title = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, quizId, name);
        return count != null && count > 0;
    }

    public Integer createQuiz(MapSqlParameterSource params) {
        String sql = "INSERT INTO quizzes (title, description, creator_id) VALUES(:title, :description, :creatorId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public Optional<Quiz> getQuizById(Integer id) {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ViewQuizGeneralDto> getAllQuizzesGeneralInfo() {
        String sql = """
                SELECT
                    qz.title,
                    COUNT(q.id) AS question_count
                FROM quizzes qz
                JOIN questions q ON q.quiz_id = qz.id
                GROUP BY qz.title
                ORDER BY qz.title;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ViewQuizGeneralDto.class));
    }

}
