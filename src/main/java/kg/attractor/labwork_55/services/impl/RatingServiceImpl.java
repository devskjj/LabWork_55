package kg.attractor.labwork_55.services.impl;

import kg.attractor.labwork_55.dao.RatingDao;
import kg.attractor.labwork_55.dto.RatingsDto;
import kg.attractor.labwork_55.dto.UserRatingDto;
import kg.attractor.labwork_55.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingDao ratingDao;

    @Override
    public RatingsDto getRatings() {
        log.info("Get top 10 ratings");
        List<UserRatingDto> topRatings = ratingDao.getTopTenRatings();

        return RatingsDto.builder()
                .ratings(topRatings)
                .build();
    }
}
