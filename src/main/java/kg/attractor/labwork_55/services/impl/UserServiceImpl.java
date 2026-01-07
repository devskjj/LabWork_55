package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public Integer registerUser(CreateUserDto dto, Authentication auth) {
        return 0;
    }

    @Override
    public UserStatisticsDto getStatistics(Integer userId, Authentication auth) {
        return null;
    }
}
