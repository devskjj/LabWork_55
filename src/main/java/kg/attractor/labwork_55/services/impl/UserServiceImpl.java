package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.UserStatisticsDao;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.exceptions.UserNotFoundException;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStatisticsDao userStatisticsDao;

    @Override
    public Integer registerUser(CreateUserDto dto, Authentication auth) {
        return 0;
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
}
