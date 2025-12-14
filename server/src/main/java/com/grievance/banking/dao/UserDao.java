package com.banking.grievance.dao;

import com.banking.grievance.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    private final BaseDao base;

    public UserDao(BaseDao base) {
        this.base = base;
    }

    private final RowMapper<User> mapper = (rs, i) -> {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setMobileNumber(rs.getString("mobile_number"));
        u.setPassword(rs.getString("password"));
        u.setAccountId(rs.getString("account_id"));
        u.setRegisteredOn(rs.getDate("registered_on"));
        return u;
    };

    // ✅ FIND BY EMAIL
    public User findByEmail(String email) {
        List<User> list = base.jdbc().query(
                "SELECT * FROM users WHERE email = ?", mapper, email);
        return list.isEmpty() ? null : list.get(0);
    }

    // ✅ FIND BY ID
    public User findById(int id) {
        List<User> list = base.jdbc().query(
                "SELECT * FROM users WHERE user_id = ?", mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // ✅ CREATE USER
    public int create(User user) {
        return base.jdbc().update(
                "INSERT INTO users (full_name, email, mobile_number, password, account_id) " +
                        "VALUES (?, ?, ?, ?, ?)",
                user.getFullName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getPassword(),
                user.getAccountId()
        );
    }
}
