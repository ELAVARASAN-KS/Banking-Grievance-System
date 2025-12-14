package com.banking.grievance.dao;

import com.banking.grievance.model.Staff;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StaffDao {
    private final BaseDao base;
    public StaffDao(BaseDao base) { this.base = base; }

    private final RowMapper<Staff> mapper = (rs, i) -> {
        Staff s = new Staff();
        s.setStaffId(rs.getInt("staff_id"));
        s.setFullName(rs.getString("full_name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setPosition(rs.getString("position"));
        s.setDepartment(rs.getString("department"));
        return s;
    };

    public List<Staff> findAll() {
        return base.jdbc().query("SELECT * FROM staff", mapper);
    }

    public Staff findById(int id) {
        List<Staff> l = base.jdbc().query("SELECT * FROM staff WHERE staff_id = ?", mapper, id);
        return l.isEmpty()? null : l.get(0);
    }

    // ===================================
    // *** NEW METHOD TO FIX LOGIN ERROR ***
    public Staff findByEmail(String email) {
        List<Staff> l = base.jdbc().query("SELECT * FROM staff WHERE email = ?", mapper, email);
        return l.isEmpty()? null : l.get(0);
    }
    // ===================================

    public List<Staff> findByDepartment(String dept) {
        return base.jdbc().query("SELECT * FROM staff WHERE department = ?", mapper, dept);
    }

    public int create(Staff s) {
        return base.jdbc().update("INSERT INTO staff (full_name,email,password,position,department) VALUES (?,?,?,?,?)",
                s.getFullName(), s.getEmail(), s.getPassword(), s.getPosition(), s.getDepartment());
    }
}
