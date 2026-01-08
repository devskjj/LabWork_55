package kg.attractor.labwork_55.controller;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.RatingsDto;
import kg.attractor.labwork_55.dto.UserStatisticsDto;
import kg.attractor.labwork_55.dto.CreateUserDto;
import kg.attractor.labwork_55.services.RatingService;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody CreateUserDto dto, Authentication auth) {
        Integer userId = userService.registerUser(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", "User with ID " + userId + " has been successfully created."));
    }

    @GetMapping("/users/{userId}/statistics")
    public ResponseEntity<?> getUserStatistics(@PathVariable Integer userId, Authentication auth) {
        UserStatisticsDto statistics = userService.getStatistics(userId, auth);
        return ResponseEntity.status(HttpStatus.OK).body(statistics);
    }

    @GetMapping("/users/ratings")
    public ResponseEntity<?> getRatings() {
        RatingsDto ratings = ratingService.getRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }
}
