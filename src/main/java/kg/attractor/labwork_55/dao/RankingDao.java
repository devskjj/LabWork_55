package kg.attractor.labwork_55.dao;

import kg.attractor.labwork_55.dto.UserRatingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RankingDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public RankingDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserRatingDto> getTopTenRatings() {
        String sql = "SELECT u.username, u.email, t.score FROM users u JOIN quiz_results qr ON u.id = qr.user_id JOIN top_scores t ON t.result_id = qr.id ORDER BY t.score DESC LIMIT 10";

        return jdbcTemplate.query(sql, (rs, rowNum) -> UserRatingDto.builder()
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .score(rs.getInt("score"))
                .build());
    }
}
