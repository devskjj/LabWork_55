package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

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

}
