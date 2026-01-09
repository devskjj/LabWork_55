package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.config.ApplicationConfig;
import kg.attractor.labwork_55.dao.UserStatisticsDao;
import kg.attractor.labwork_55.dao.UserDao;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.exceptions.FailedToCreateException;
import kg.attractor.labwork_55.exceptions.UserNotFoundException;
import kg.attractor.labwork_55.exceptions.UserNotFoundException;
import kg.attractor.labwork_55.models.User;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStatisticsDao userStatisticsDao;
    private final UserDao userDao;

    @Override
    public Integer registerUser(CreateUserDto dto) {
        log.info(
                "Creating user: username='{}', email='{}'",
                dto.getUsername(),
                dto.getEmail()
        );
        if (userDao.isAlreadyExists(dto.getEmail())) {
            log.warn("User creation failed: email '{}' already exists", dto.getEmail());
            throw new FailedToCreateException("This email is already registered.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username",dto.getUsername())
                .addValue("password", ApplicationConfig.passwordEncoder().encode(dto.getPassword()))
                .addValue("email", dto.getEmail())
                .addValue("role", "USER")
                .addValue("enabled", true);
        try {
            Integer userId = userDao.createUser(params);
            log.info(
                    "User created successfully: userId={}, email='{}'",
                    userId,
                    dto.getEmail()
            );
            return userId;

        } catch (NullPointerException npe) {
            log.error(
                    "User creation failed due to missing generated id: email='{}'",
                    dto.getEmail(),
                    npe
            );
            throw new FailedToCreateException("Failed to create a new user: Id was not generated.");
        }
    }

    @Override
    public UserStatisticsDto getStatistics(Integer userId, Authentication auth) {
        if (!userStatisticsDao.userExists(userId)) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }

        log.info("Getting statistics for user {}", userId);

        Map<String, Object> statistics = userStatisticsDao.getStatistics(userId);
        return UserStatisticsDto.builder()
                .statistics(statistics)
                .build();
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }
}
