package com.banking.grievance.dao;

import com.banking.grievance.model.Admin;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {

    private final BaseDao base;

    public AdminDao(BaseDao base) {
        this.base = base;
    }

    private final RowMapper<Admin> mapper = (rs, i) -> {
        Admin a = new Admin();
        a.setAdminUsername(rs.getString("username"));
        a.setPassword(rs.getString("password"));
        a.setFullName(rs.getString("full_name"));
        a.setAdminEmail(rs.getString("email"));
        return a;
    };

    public Admin findByUsername(String username) {
        List<Admin> list = base.jdbc().query(
                "SELECT * FROM admins WHERE username = ?",
                mapper,
                username
        );
        return list.isEmpty() ? null : list.get(0);
    }
    public int updatePassword(String username, String hash) {
        return base.jdbc().update(
                "UPDATE admins SET password = ? WHERE username = ?",
                hash, username
        );
    }

}
