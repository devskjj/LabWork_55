package kg.attractor.labwork_55.services;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuizService {
    Integer createQuiz(@Valid CreateQuizDto dto, Authentication auth);

    Integer createQuestion(Integer quizId, List<CreateQuestionDto> questions);

    void createOption(Integer questionId, List<CreateOptionDto> options);

    List<ViewQuizGeneralDto> getAllQuizzes();

    ViewQuizDetailedDto getQuizById(Integer quizId);

    void submitQuizAnswers(PathVariable quizId, @Valid UserAnswerDto answers, Authentication auth);

    QuizResultsDto getQuizResults(PathVariable quizId, Authentication auth);

    void submitQuizRating(PathVariable quizId, Authentication auth);

    QuizLeaderboardDto getQuizLeaderBoard(PathVariable quizId);
}
