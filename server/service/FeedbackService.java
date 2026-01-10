package com.grievance.service;

import com.grievance.entity.Feedback;
import com.grievance.entity.Grievance;
import com.grievance.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback submitFeedback(Grievance grievance, Integer rating, String comments) {
        if (feedbackRepository.existsByGrievance(grievance)) {
            throw new RuntimeException("Feedback already submitted for this grievance");
        }

        if (!grievance.getStatus().equals(Grievance.Status.RESOLVED)) {
            throw new RuntimeException("Grievance must be resolved before submitting feedback");
        }

        Feedback feedback = new Feedback();
        feedback.setGrievance(grievance);
        feedback.setRating(rating);
        feedback.setComments(comments);

        return feedbackRepository.save(feedback);
    }

    public Optional<Feedback> getFeedbackByGrievance(Grievance grievance) {
        return feedbackRepository.findByGrievance(grievance);
    }

    public boolean hasFeedback(Grievance grievance) {
        return feedbackRepository.existsByGrievance(grievance);
    }
}




