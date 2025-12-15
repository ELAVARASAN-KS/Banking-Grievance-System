package com.banking.grievance.model;

public class ComplaintStatistics {

    private int totalComplaints;
    private int resolved;
    private int inProgress;
    private int unresolved;
    private int escalated;
    private int pending;

    public ComplaintStatistics() {}

    public int getTotalComplaints() {
        return totalComplaints;
    }
    public void setTotalComplaints(int totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public int getResolved() {
        return resolved;
    }
    public void setResolved(int resolved) {
        this.resolved = resolved;
    }

    public int getInProgress() {
        return inProgress;
    }
    public void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    public int getUnresolved() {
        return unresolved;
    }
    public void setUnresolved(int unresolved) {
        this.unresolved = unresolved;
    }

    public int getEscalated() {
        return escalated;
    }
    public void setEscalated(int escalated) {
        this.escalated = escalated;
    }

    public int getPending() {
        return pending;
    }
    public void setPending(int pending) {
        this.pending = pending;
    }
}
