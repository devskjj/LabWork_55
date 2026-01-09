package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.models.User;
import lombok.RequiredArgsConstructor;
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
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public Integer createUser(MapSqlParameterSource params) {
        NamedParameterJdbcTemplate parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String sql = "INSERT INTO users (username, password, email, enabled, role_id) VALUES (:username, :password, :email, :enabled, (SELECT id FROM roles WHERE role = :role))";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterJdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public Optional<User> getUserById (Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean isAlreadyExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }


}
