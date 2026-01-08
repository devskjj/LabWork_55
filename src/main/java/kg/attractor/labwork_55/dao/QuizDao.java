package kg.attractor.labwork_55.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

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

    public boolean isAlreadyExists(Integer userId, String name) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE creator_id = ? AND title = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, name);
        return count != null && count > 0;
    }

    public Integer createQuiz(MapSqlParameterSource params) {
        String sql = "INSERT INTO quizzes (title, description, creator_id) VALUES(:title, :description, :creatorId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
