package kg.attractor.labwork_55.controller;

import jakarta.validation.Valid;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDto dto, Authentication auth) {
        Integer userId = userService.registerUser(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result","User with ID " + userId + " has been successfully created."));
    }

    @GetMapping("/users/{userId}/statistics")
    public ResponseEntity<?> getUserStatistics(@PathVariable Integer userId, Authentication auth) {
        Map<String, Object> statistics = userService.getStatistics(userId, auth);
        return ResponseEntity.status((HttpStatus.OK).body(statistics));
    }
}
