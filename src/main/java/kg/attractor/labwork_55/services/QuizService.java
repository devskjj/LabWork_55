package kg.attractor.labwork_55.services;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.models.Option;
import kg.attractor.labwork_55.models.Question;
import kg.attractor.labwork_55.models.Quiz;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuizService {
    Integer createQuizFull(CreateQuizDto dto, Authentication auth);

    Integer createQuiz(@Valid CreateQuizDto dto, Authentication auth);

    Integer createQuestion(Integer quizId, CreateQuestionDto question);

    void createOption(Integer questionId, CreateOptionDto option);

    List<ViewQuizGeneralDto> getAllQuizzes();

    ViewQuizDetailedDto getQuizDetailedDtoById(Integer quizId);

    Quiz getQuizById(Integer quizId);

    Question getQuestionById(Integer questionId);

    Option getOptionById(Integer optionId);

    void submitQuizAnswers(PathVariable quizId, @Valid UserAnswerDto answers, Authentication auth);

    QuizResultsDto getQuizResults(PathVariable quizId, Authentication auth);

    void submitQuizRating(PathVariable quizId, Authentication auth);

    QuizLeaderboardDto getQuizLeaderBoard(PathVariable quizId);
}
