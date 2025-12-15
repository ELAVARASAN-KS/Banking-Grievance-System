package com.banking.grievance.dao;
import com.banking.grievance.dto.ResolveComplaintRequest;

import com.banking.grievance.model.Staff;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

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

    public boolean resolveComplaint(int complaintId, int staffId, String note) {

        // 1. Update complaint status
        int updated = base.jdbc().update("""
        UPDATE complaints
        SET status = 'RESOLVED',
            staff_id = ?,
            resolved_at = NOW()
        WHERE complaint_id = ?
    """, staffId, complaintId);

        if (updated == 0) return false;

        // 2. Insert resolution note
        base.jdbc().update("""
        INSERT INTO complaint_notes (complaint_id, staff_id, note)
        VALUES (?, ?, ?)
    """, complaintId, staffId, note);

        return true;
    }

    @PostMapping("/resolve")
    public ResponseEntity<?> resolveComplaint(
            @RequestBody ResolveComplaintRequest req) {

        boolean ok = service.resolveComplaint(
                req.getComplaintId(),
                req.getStaffId(),
                req.getNote()
        );

        if (!ok) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Resolve failed"));
        }

        return ResponseEntity.ok(
                Map.of("success", true, "message", "Complaint resolved successfully")
        );
    }

}