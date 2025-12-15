package com.banking.grievance.model;

public class Feedback {

    private Integer feedbackId;
    private Integer complaintId;
    private Integer rating;
    private String feedbackText;

    public Feedback() {}

    public Integer getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getComplaintId() {
        return complaintId;
    }
    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }
}
