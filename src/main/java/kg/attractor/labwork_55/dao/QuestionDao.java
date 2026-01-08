package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.models.Question;
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
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public QuestionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer createQuestion(MapSqlParameterSource params) {
        String sql = "INSERT INTO questions (quiz_id, question_text) VALUES(:quizId, :questionText)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public Optional<Question> getQuestionById(Integer id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> getQuestionByOption (Integer id) {
        String sql = "SELECT question_id FROM options WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
