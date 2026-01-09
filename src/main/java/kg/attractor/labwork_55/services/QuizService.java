package kg.attractor.labwork_55.services;

import jakarta.validation.Valid;
import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.models.Option;
import kg.attractor.labwork_55.models.Question;
import kg.attractor.labwork_55.models.Quiz;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuizService {
    Integer createQuizFull(CreateQuizDto dto, Authentication auth);

    Integer createQuiz(@Valid CreateQuizDto dto, Authentication auth);

    Integer createQuestion(Integer quizId, CreateQuestionDto question);

    void createOption(Integer questionId, CreateOptionDto option);

    List<ViewQuizGeneralDto> getAllQuizzes();

    ViewQuizDetailedDto getQuizDetailedDtoById(Integer quizId, int page, int size, Authentication auth);

    Quiz getQuizById(Integer quizId);

    Question getQuestionById(Integer questionId);

    Question getQuestionByOption (Integer optionId);

    Quiz getQuizByQuestion (Integer questionId);

    Option getOptionById(Integer optionId);

    void submitQuizAnswers(Integer quizId, List<UserAnswerDto> answers, Authentication auth);

    QuizResultsDto getQuizResults(Integer quizId, Authentication auth, int page, int size);

    void submitQuizRating(Integer quizId, QuizRatingDto rating, Authentication auth);

    QuizLeaderboardDto getQuizLeaderBoard(Integer quizId);
}
