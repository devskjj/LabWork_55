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
            log.warn("Quiz with id {} does not exist", quizId);
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        log.info("Quiz with id {} found", quizId);
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
    public Question getQuestionByOption(Integer optionId) {
        Integer questionId = questionDao.getQuestionByOption(optionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question for option id " + optionId + " not found."));
        return getQuestionById(questionId);
    }

    @Override
    public Quiz getQuizByQuestion(Integer questionId) {
        Integer quizId = quizDao.getQuizByQuestion(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Quiz for question id " + questionId + " not found."));
        return getQuizById(quizId);
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
        log.info("Submitting answers for user: {} (id={}) to quizId={}", email, user.getId(), quizId);

        Quiz quiz = getQuizById(quizId);
        log.info("Fetched quiz: {} (id={})", quiz.getTitle(), quiz.getId());

        answers.forEach(a -> {
            Question question = getQuestionById(a.getQuestionId());
            Option option = getOptionById(a.getOptionId());
            log.info("Validating answer: questionId={}, optionId={}", a.getQuestionId(), a.getOptionId());
            log.info("Fetched question: {} (id={})", question.getQuestionText(), question.getId());
            log.info("Fetched option: {} (id={})", option.getOptionText(), option.getId());

            if (!Objects.equals(question.getId(), getQuestionByOption(option.getId()).getId())) {
                log.error("Option {} does not belong to question {}", option.getId(), question.getId());
                throw new FailedToCreateException("Option " + option.getId() + " does not belong to this question.");
            }
            if (!Objects.equals(quizId, getQuizByQuestion(question.getId()).getId())) {
                log.error("Question {} does not belong to quiz {}", question.getId(), quizId);
                throw new FailedToCreateException("Question " + question.getId() + " does not belong to this quiz.");
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
            log.info("Inserting answer: {}", params.getValues());
            try {
                quizDao.submitUserAnswers(params);
                log.info("Successfully inserted answer for questionId={}", uad.getQuestionId());
            } catch (DataAccessException dae) {
                log.error("Failed to insert answer for questionId={}", uad.getQuestionId(), dae);
                throw new FailedToCreateException("Failed to save user answer because user already answered that question.");
            }
        }
        log.info("All answers submitted successfully for quizId={}", quizId);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getId())
                .addValue("quizId", quiz.getId())
                .addValue("score", quizResultDao.getUserScore(quizId, email));
        log.info("Preparing to submit quiz score for userId={}, quizId={}, score={}", user.getId(), quiz.getId(), params.getValue("score"));
        try {
            quizResultDao.submitScore(params);
            log.info("Successfully submitted quiz score for userId={}, quizId={}", user.getId(), quiz.getId());
        } catch (DataAccessException dae) {
            log.error("Failed to save quiz score for userId={}, quizId={}, params={}", user.getId(), quiz.getId(), params.getValues(), dae);
            throw new FailedToCreateException("Failed to save quiz score because user already scored that quiz.");
        }
    }

    @Override
    public QuizResultsDto getQuizResults(Integer quizId, Authentication auth) {
        if (!quizResultDao.quizExists(quizId)) {
            log.warn("No quiz exists for quizId={}", quizId);
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();

        log.info("Getting quiz results for quizId={}", quizId);
        User user = userService.getUserByEmail(email);
        return quizResultDao.getQuizResults(quizId, user);
    }

    @Override
    public void submitQuizRating(Integer quizId, QuizRatingDto rating, Authentication auth) {
        UserDetails userAuth = (UserDetails) auth.getPrincipal();
        String email = Objects.requireNonNull(userAuth).getUsername();
        User user = userService.getUserByEmail(email);
        log.info("User '{}' (id={}) is submitting rating for quizId={}", email, user.getId(), quizId);
        log.info("Submitted rating value: {}", rating.getRate());
        Quiz quiz = getQuizById(quizId);
        log.info("Fetched quiz: '{}' (id={})", quiz.getTitle(), quiz.getId());
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("quizRateByUser", rating.getRate())
                .addValue("userId", user.getId())
                .addValue("quizId", quizId);

        log.info("Executing update with params: {}", params.getValues());
        int updated = quizDao.submitQuizRating(params);
        if (updated == 0) {
            log.warn("No quiz result found for userId={} and quizId={}. Cannot update rating.", user.getId(), quizId);
            throw new FailedToCreateException("Quiz result not found. Cannot update rating.");
        }
    }

    @Override
    public QuizLeaderboardDto getQuizLeaderBoard(Integer quizId) {
        if (!quizLeaderboardDao.quizExists(quizId)) {
            log.warn("Quiz with id '{}' not found", quizId);
            throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
        }
        LinkedHashMap<String, Integer> leaderboard = quizLeaderboardDao.getLeaderboard(quizId);
        log.info("Fetched leaderboard: {}", leaderboard);
        return QuizLeaderboardDto.builder()
                .leaderboard(leaderboard)
                .build();
    }
}
