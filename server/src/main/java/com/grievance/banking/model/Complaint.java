package com.banking.grievance.model;

import java.sql.Date;

public class Complaint {

    private Integer complaintId;
    private String complaintNumber;
    private Integer userId;

    private String subject;
    private String category;
    private String description;

    private String status;
    private Integer staffId;

    private Date dateRaised;
    private Date dateResolved;

    private String resolutionNotes;

    public Complaint() {}

    public Integer getComplaintId() {
        return complaintId;
    }
    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintNumber() {
        return complaintNumber;
    }
    public void setComplaintNumber(String complaintNumber) {
        this.complaintNumber = complaintNumber;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStaffId() {
        return staffId;
    }
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Date getDateRaised() {
        return dateRaised;
    }
    public void setDateRaised(Date dateRaised) {
        this.dateRaised = dateRaised;
    }

    public Date getDateResolved() {
        return dateResolved;
    }
    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }
    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }
}
