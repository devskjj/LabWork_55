package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.QuestionDao;
import kg.attractor.labwork_55.dao.QuizDao;
import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.exceptions.FailedToCreateException;
import kg.attractor.labwork_55.models.User;
import kg.attractor.labwork_55.services.QuizService;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private UserService userService;
    private QuizDao quizDao;
    private QuestionDao questionDao;

    @Override
    public Integer createQuiz(CreateQuizDto dto, Authentication auth) {
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();
        User user = userService.getUserByEmail(email);
        if (quizDao.isAlreadyExists(user.getId(), dto.getTitle())) {
            throw new FailedToCreateException("Such quiz name already exists for the same creator.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("creatorId", user.getId())
                .addValue("title", dto.getTitle())
                .addValue("description", dto.getDescription());
        try {
            return quizDao.createQuiz(params);
        } catch (NullPointerException npe) {
            throw new FailedToCreateException("Failed to create a new quiz: Id was not generated.");
        }
    }

    @Override
    public Integer createQuestion(Integer quizId, List<CreateQuestionDto> questions) {
        getQuizById(quizId);
        if (questions.isEmpty()) {
            throw new FailedToCreateException("Question cannot be created for the quiz because there is no data.");
        }
        for (CreateQuestionDto question : questions) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("quizId", quizId)
                    .addValue("questionText", question.getQuestionText());
            try {
                return questionDao.createQuestion(params);
            } catch (NullPointerException npe) {
                throw new FailedToCreateException("Failed to create a new question for the quiz: Id was not generated.");
            }
        }
    }

    @Override
    public void createOption(Integer questionId, List<CreateOptionDto> options) {

    }

    @Override
    public List<ViewQuizGeneralDto> getAllQuizzes() {
        return List.of();
    }

    @Override
    public ViewQuizDetailedDto getQuizById(Integer quizId) {
        return null;
    }

    @Override
    public void submitQuizAnswers(PathVariable quizId, UserAnswerDto answers, Authentication auth) {

    }

    @Override
    public QuizResultsDto getQuizResults(PathVariable quizId, Authentication auth) {
        return null;
    }

    @Override
    public void submitQuizRating(PathVariable quizId, Authentication auth) {

    }

    @Override
    public QuizLeaderboardDto getQuizLeaderBoard(PathVariable quizId) {
        return null;
    }
}
