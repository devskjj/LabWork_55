package kg.attractor.labwork_55.controller;

import jakarta.validation.Valid;
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

    @PostMapping()
    public ResponseEntity<?> createNewQuiz(@Valid @RequestBody QuizDto dto, Authentication auth) {
        Integer quizId = quizService.createQuiz(dto, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result","Quiz with ID " + quizId + " has been successfully created."));
    }

    @GetMapping()
    public ResponseEntity<?> getQuizListGeneralInfo() {
        List<QuizGeneralDto> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.status(HttpStatus.OK).body(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuizByIdDetailedInfo(@PathVariable Integer quizId) {
        QuizDetailedDto quiz = quizService.getQuizById(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

    @PostMapping("/{quizId}/solve")
    public ResponseEntity<?> submitAnswers(PathVariable quizId, @Valid @RequestBody AnswerDto answers, Authentication auth) {
        quizService.submitQuizAnswers(quizId, answers, auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("result","Answers to the quiz with ID " + quizId + " has been successfully submitted."));
    }

    @GetMapping("/{quizId}/results")
    public ResponseEntity<?> getResults(PathVariable quizId, Authentication auth) {
        ResultsDto results = quizService.getQuizResults(quizId, auth);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @PostMapping("/{quizId}/rate")
    public ResponseEntity<?> rateQuiz(PathVariable quizId, Authentication auth) {
        quizService.submitQuizRating(quizId, auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("result","Rating of the quiz with ID " + quizId + " has been successfully updated."));
    }

    @GetMapping("/{quizId}/leaderboard")
    public ResponseEntity<?> getLeaderBoard(PathVariable quizId) {
        LeaderboardDto leaderboard = quizService.getQuizLeaderBoard(quizId);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboard);
    }
}
