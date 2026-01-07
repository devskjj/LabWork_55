package kg.attractor.labwork_55.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
}
