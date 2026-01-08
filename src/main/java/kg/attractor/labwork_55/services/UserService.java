package kg.attractor.labwork_55.services;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.models.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    Integer registerUser(@Valid CreateUserDto dto);

    UserStatisticsDto getStatistics(Integer userId, Authentication auth);

    User getUserById(Integer id);

    User getUserByEmail(String email);
}
