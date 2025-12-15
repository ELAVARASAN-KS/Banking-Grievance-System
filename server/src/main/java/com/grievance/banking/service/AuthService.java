package com.banking.grievance.service;

import com.banking.grievance.dao.UserDao;
import com.banking.grievance.dao.AdminDao;
import com.banking.grievance.dao.StaffDao;
import com.banking.grievance.model.User;
import com.banking.grievance.model.Admin;
import com.banking.grievance.model.Staff;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserDao userDao;
    private final AdminDao adminDao;
    private final StaffDao staffDao;
    private final PasswordEncoder passwordEncoder;

    // ✅ ONLY ONE CONSTRUCTOR
    public AuthService(UserDao userDao,
                       AdminDao adminDao,
                       StaffDao staffDao,
                       PasswordEncoder passwordEncoder) {

        this.userDao = userDao;
        this.adminDao = adminDao;
        this.staffDao = staffDao;
        this.passwordEncoder = passwordEncoder;

        // 🔴 TEMPORARY – RUN ONCE, THEN REMOVE
        // resetAdminPassword("admin", "admin123");
    }

    /* ================= USER REGISTER ================= */
    public String registerUser(String fullName,
                               String email,
                               String mobile,
                               String password,
                               String accountId) {

        if (userDao.findByEmail(email) != null) {
            return "USER_ALREADY_EXISTS";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setMobileNumber(mobile);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountId(accountId);

        userDao.create(user);
        return "REGISTER_SUCCESS";
    }

    /* ================= USER LOGIN ================= */
    public String userLogin(String email, String password) {

        User user = userDao.findByEmail(email);

        if (user == null)
            return "INVALID_EMAIL";

        if (!passwordEncoder.matches(password, user.getPassword()))
            return "INVALID_PASSWORD";

        return "LOGIN_SUCCESS";
    }

    /* ================= ADMIN LOGIN ================= */
    public String adminLogin(String username, String password) {

        Admin admin = adminDao.findByUsername(username);

        if (admin == null)
            return "INVALID_ADMIN";

        if (!passwordEncoder.matches(password, admin.getPassword()))
            return "INVALID_PASSWORD";

        return "ADMIN_LOGIN_SUCCESS";
    }

    /* ================= STAFF LOGIN ================= */
    public String staffLogin(String email, String password) {

        Staff staff = staffDao.findByEmail(email);

        if (staff == null)
            return "INVALID_STAFF";

        if (!passwordEncoder.matches(password, staff.getPassword()))
            return "INVALID_PASSWORD";

        return "STAFF_LOGIN_SUCCESS";
    }

    // ================= TEMPORARY RESET METHOD =================
    public void resetAdminPassword(String username, String rawPassword) {
        String hash = passwordEncoder.encode(rawPassword);
        System.out.println("NEW ADMIN HASH = " + hash);
        adminDao.updatePassword(username, hash);
    }
}
