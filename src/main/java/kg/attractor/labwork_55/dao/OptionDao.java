package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.models.Option;
import kg.attractor.labwork_55.models.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public OptionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<Option> getOptionById(Integer id) {
        String sql = "SELECT * FROM options WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Option.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
