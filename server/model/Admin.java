package com.banking.grievance.model;

public class Admin {

    private int adminId;
    private String adminEmail;
    private String adminUsername;
    private String password;
    private String fullName;

    // ===== GETTERS =====
    public int getAdminId() {
        return adminId;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    // ===== SETTERS =====
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
