package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.RankingDao;
import kg.attractor.labwork_55.dto.RatingsDto;
import kg.attractor.labwork_55.dto.UserRatingDto;
import kg.attractor.labwork_55.exceptions.EntityNotFoundException;
import kg.attractor.labwork_55.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RankingDao rankingDao;

    @Override
    public RatingsDto getRatings() {
        List<UserRatingDto> topRatings = rankingDao.getTopTenRatings();

        if (topRatings == null || topRatings.isEmpty()) {
            log.warn("Top ratings not found");
            throw new EntityNotFoundException("Top ratings not found");
        }

        log.info("Get top 10 ratings");
        return RatingsDto.builder()
                .ratings(topRatings)
                .build();
    }
}
