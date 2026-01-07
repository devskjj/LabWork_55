package kg.attractor.labwork_55.services;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import org.springframework.security.core.Authentication;

public interface UserService {
    Integer registerUser(@Valid CreateUserDto dto, Authentication auth);

    UserStatisticsDto getStatistics(Integer userId, Authentication auth);
}
