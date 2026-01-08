package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.*;
import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.exceptions.QuizNotFoundException;
import kg.attractor.labwork_55.exceptions.FailedToCreateException;
import kg.attractor.labwork_55.exceptions.OptionNotFoundException;
import kg.attractor.labwork_55.exceptions.QuestionNotFoundException;
import kg.attractor.labwork_55.models.Option;
import kg.attractor.labwork_55.models.Question;
import kg.attractor.labwork_55.models.Quiz;
import kg.attractor.labwork_55.models.User;
import kg.attractor.labwork_55.services.QuizService;
import kg.attractor.labwork_55.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizLeaderboardDao quizLeaderboardDao;
    private final ViewQuizDetailedDao viewQuizDetailedDao;
    private final QuizResultDao quizResultDao;
    private final UserService userService;
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final OptionDao optionDao;

    public Integer createQuizFull(CreateQuizDto dto, Authentication auth) {
        Integer quizId = createQuiz(dto, auth);
        List<CreateQuestionDto> questions = dto.getQuestions();
        for (CreateQuestionDto q : questions) {
            Integer questionId = createQuestion(quizId, q);
            List<CreateOptionDto> options = q.getOptions();
            for (CreateOptionDto o : options) {
                createOption(questionId, o);
            }
        }
        return quizId;
    }

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
    public Integer createQuestion(Integer quizId, CreateQuestionDto question) {
        getQuizById(quizId);
        if (question == null) {
            throw new FailedToCreateException("Question cannot be created for the quiz because there is no data.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("quizId", quizId)
                .addValue("questionText", question.getQuestionText());
        try {
            return questionDao.createQuestion(params);
        } catch (NullPointerException npe) {
            throw new FailedToCreateException("Failed to create a new question for the quiz: Id was not generated.");
        }
    }

    @Override
    public void createOption(Integer questionId, CreateOptionDto option) {
        getQuestionById(questionId);
        if (option == null) {
            throw new FailedToCreateException("Option cannot be created for the question because there is no data.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("questionId", questionId)
                .addValue("optionText", option.getOptionText())
                .addValue("isCorrect", option.getIsCorrect());
        try {
            optionDao.createOption(params);
        } catch (NullPointerException npe) {
            throw new FailedToCreateException("Failed to create a new option for the question: Id was not generated.");
        }
    }

    @Override
    public List<ViewQuizGeneralDto> getAllQuizzes() {
        return quizDao.getAllQuizzesGeneralInfo();
    }

    @Override
    public ViewQuizDetailedDto getQuizDetailedDtoById(Integer quizId) {
        if (!viewQuizDetailedDao.quizExists(quizId)) {
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        return viewQuizDetailedDao.getQuizDetails(quizId);
    }

    @Override
    public Quiz getQuizById(Integer quizId) {
        return quizDao.getQuizById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz with id " + quizId + " not found."));
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return questionDao.getQuestionById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id " + questionId + " not found."));
    }

    @Override
    public Question getQuestionByOption (Integer optionId) {
        return questionDao.getQuestionByOption(optionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question for option id " + optionId + " not found."));
    }

    @Override
    public Quiz getQuizByQuestion (Integer questionId) {
        return quizDao.getQuizByQuestion(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Quiz for question id " + questionId + " not found."));
    }

    @Override
    public Option getOptionById(Integer optionId) {
        return optionDao.getOptionById(optionId)
                .orElseThrow(() -> new OptionNotFoundException("Option with id " + optionId + " not found."));
    }

    @Override
    public void submitQuizAnswers(Integer quizId, List<UserAnswerDto> answers, Authentication auth) {
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();
        User user = userService.getUserByEmail(email);
        Quiz quiz = getQuizById(quizId);
        answers.forEach(a -> {
            Question question = getQuestionById(a.getQuestionId());
            Option option = getOptionById(a.getOptionId());
            if (!Objects.equals(question.getId(), getQuestionByOption(option.getId()).getId())) {
                throw new FailedToCreateException("Option does not belong to this question.");
            }
            if (!Objects.equals(quizId, getQuizByQuestion(question.getId()).getId())) {
                throw new FailedToCreateException("Question does not belong to this quiz.");
            }
        });
        LocalDateTime now = LocalDateTime.now();
        for (UserAnswerDto uad : answers) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", user.getId())
                    .addValue("quizId", quiz.getId())
                    .addValue("questionId", uad.getQuestionId())
                    .addValue("optionId", uad.getOptionId())
                    .addValue("answeredAt", now);
            try {
                quizDao.submitUserAnswers(params);
            } catch (DataAccessException dae) {
                throw new FailedToCreateException("Failed to save user answers.");
            }
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("quizId", quiz.getId())
                .addValue("score", );
        try {
            quizResultDao.submitScore(params);
        } catch (DataAccessException dae) {
            throw new FailedToCreateException("Failed to save quiz score.");
        }

//        name: quiz_rate_by_user

    }

    @Override
    public QuizResultsDto getQuizResults(Integer quizId, Authentication auth) {
        if (!quizResultDao.quizExists(quizId)) {
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();
        return quizResultDao.getQuizResults(quizId, email);
    }

    @Override
    public void submitQuizRating(Integer quizId, Authentication auth) {
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();
        User user = userService.getUserByEmail(email);
        Quiz quiz = getQuizById(quizId);

    }

    @Override
    public QuizLeaderboardDto getQuizLeaderBoard(Integer quizId) {
        if (!quizLeaderboardDao.quizExists(quizId)) {
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        LinkedHashMap<String, Integer> leaderboard = quizLeaderboardDao.getLeaderboard(quizId);
        return QuizLeaderboardDto.builder()
                .leaderboard(leaderboard)
                .build();
    }
}
