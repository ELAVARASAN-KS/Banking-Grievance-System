package com.banking.grievance.service;

import com.banking.grievance.dao.FeedbackDao;
import com.banking.grievance.model.Feedback;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackDao dao;

    public FeedbackService(FeedbackDao dao) {
        this.dao = dao;
    }

    public boolean submitFeedback(Feedback feedback) {
        return dao.create(feedback) > 0;
    }

    public List<Feedback> getFeedbackForComplaint(int cid) {
        return dao.findByComplaintId(cid);
    }

    public List<Feedback> getByComplaintId(int complaintId) {
        return dao.findByComplaintId(complaintId);
    }
}
