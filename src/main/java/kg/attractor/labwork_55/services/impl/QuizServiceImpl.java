package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dto.*;
import kg.attractor.labwork_55.services.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    @Override
    public Integer createQuiz(CreateQuizDto dto, Authentication auth) {
        return 0;
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
