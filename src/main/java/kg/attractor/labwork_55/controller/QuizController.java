package kg.attractor.labwork_55.controller;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    @PostMapping()
    public ResponseEntity<?> createNewQuiz(@Valid @RequestBody CreateQuizDto dto, Authentication auth) {
        Integer quizId = quizService.createQuizFull(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", "Quiz with ID " + quizId + " has been successfully created."));
    }

    @GetMapping()
    public ResponseEntity<?> getQuizListGeneralInfo() {
        List<ViewQuizGeneralDto> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.status(HttpStatus.OK).body(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuizByIdDetailedInfo(@PathVariable Integer quizId) {
        ViewQuizDetailedDto quiz = quizService.getQuizDetailedDtoById(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

    @PostMapping("/{quizId}/solve")
    public ResponseEntity<?> submitAnswers(@PathVariable Integer quizId, @Valid @RequestBody List<@Valid UserAnswerDto> answers, Authentication auth) {
        quizService.submitQuizAnswers(quizId, answers, auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("result", "Answers to the quiz with ID " + quizId + " have been successfully submitted."));
    }

    @GetMapping("/{quizId}/results")
    public ResponseEntity<?> getResults(@PathVariable Integer quizId, Authentication auth) {
        QuizResultsDto results = quizService.getQuizResults(quizId, auth);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @PostMapping("/{quizId}/rate")
    public ResponseEntity<?> rateQuiz(@PathVariable Integer quizId, @Valid @RequestBody Integer rate, Authentication auth) {
        quizService.submitQuizRating(quizId, auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("result", "Rating of the quiz with ID " + quizId + " has been successfully updated."));
    }

    @GetMapping("/{quizId}/leaderboard")
    public ResponseEntity<?> getLeaderBoard(@PathVariable Integer quizId) {
        QuizLeaderboardDto leaderboard = quizService.getQuizLeaderBoard(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboard);
    }
}
