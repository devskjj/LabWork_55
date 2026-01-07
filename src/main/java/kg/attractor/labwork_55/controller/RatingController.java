package kg.attractor.labwork_55.controller;

import kg.attractor.labwork_55.dto.RatingsDto;
import kg.attractor.labwork_55.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/ratings")
    public ResponseEntity<?> getRatings() {
        RatingsDto ratings = ratingService.getRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }
}
