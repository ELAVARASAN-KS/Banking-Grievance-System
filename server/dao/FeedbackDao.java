package com.banking.grievance.dao;

import com.banking.grievance.model.Feedback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FeedbackDao {
    private final BaseDao base;
    public FeedbackDao(BaseDao base) { this.base = base; }

    private final RowMapper<Feedback> mapper = (rs,i) -> {
        Feedback f = new Feedback();
        f.setFeedbackId(rs.getInt("feedback_id"));
        f.setComplaintId(rs.getInt("complaint_id"));
        f.setRating(rs.getInt("rating"));
        f.setFeedbackText(rs.getString("feedback_text"));
        return f;
    };

    public int create(Feedback f) {
        return base.jdbc().update("INSERT INTO feedback (complaint_id,rating,feedback_text) VALUES (?,?,?)",
                f.getComplaintId(), f.getRating(), f.getFeedbackText());
    }

    public List<Feedback> findByComplaintId(int complaintId) {
        return base.jdbc().query("SELECT * FROM feedback WHERE complaint_id = ?", mapper, complaintId);
    }
}
