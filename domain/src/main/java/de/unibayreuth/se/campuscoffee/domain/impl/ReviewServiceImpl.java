package de.unibayreuth.se.campuscoffee.domain.impl;

import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewDataService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDataService reviewDataService;

    @Override
    public void clear() {
        reviewDataService.clear();
    }

    @Override
    public List<Review> getAll() {
        return reviewDataService.getAll();
    }

    @Override
    public Review getById(Long id) throws ReviewNotFoundException {
        return null;
    }

    @Override
    public List<Review> getApprovedByPos(Pos pos) throws PosNotFoundException {
        return reviewDataService.getByPos(pos);
    }

    @Override
    public Review create(Review review) throws PosNotFoundException, UserNotFoundException {
        return null;
    }

    @Override
    public Review approve(Review review, User user) throws ReviewNotFoundException, UserNotFoundException, IllegalArgumentException {
        return null;
    }
}
