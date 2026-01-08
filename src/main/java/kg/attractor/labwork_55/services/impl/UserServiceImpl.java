package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.UserDao;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.exceptions.UserNotFoundException;
import kg.attractor.labwork_55.models.User;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public Integer registerUser(CreateUserDto dto, Authentication auth) {
        return 0;
    }

    @Override
    public UserStatisticsDto getStatistics(Integer userId, Authentication auth) {
        return null;
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
