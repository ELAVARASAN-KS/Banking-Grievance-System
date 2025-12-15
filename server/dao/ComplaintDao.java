package com.banking.grievance.dao;

import com.banking.grievance.model.Complaint;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ComplaintDao {
    private final BaseDao base;
    public ComplaintDao(BaseDao base) { this.base = base; }

    private final RowMapper<Complaint> mapper = (rs, i) -> {
        Complaint c = new Complaint();
        c.setComplaintId(rs.getInt("complaint_id"));
        c.setComplaintNumber(rs.getString("complaint_number"));
        c.setUserId(rs.getInt("user_id"));
        c.setSubject(rs.getString("subject"));
        c.setCategory(rs.getString("category"));
        c.setDescription(rs.getString("description"));
        c.setStatus(rs.getString("status"));
        c.setStaffId(rs.getObject("staff_id") == null ? null : rs.getInt("staff_id"));
        c.setDateRaised(rs.getDate("date_raised"));
        c.setDateResolved(rs.getDate("date_resolved"));
        c.setResolutionNotes(rs.getString("resolution_notes"));
        return c;
    };

    public List<Complaint> findAll() {
        return base.jdbc().query("SELECT * FROM complaints ORDER BY date_raised DESC", mapper);
    }

    public Complaint findById(int id) {
        List<Complaint> l = base.jdbc().query("SELECT * FROM complaints WHERE complaint_id = ?", mapper, id);
        return l.isEmpty()? null : l.get(0);
    }

    public Complaint findByNumber(String number) {
        List<Complaint> l = base.jdbc().query("SELECT * FROM complaints WHERE complaint_number = ?", mapper, number);
        return l.isEmpty()? null : l.get(0);
    }

    public List<Complaint> findByUserId(int userId) {
        return base.jdbc().query("SELECT * FROM complaints WHERE user_id = ? ORDER BY date_raised DESC", mapper, userId);
    }

    public List<Complaint> findByStaffId(int staffId) {
        return base.jdbc().query("SELECT * FROM complaints WHERE staff_id = ? ORDER BY date_raised DESC", mapper, staffId);
    }

    public int create(Complaint c) {
        return base.jdbc().update(
                "INSERT INTO complaints (complaint_number,user_id,subject,category,description,status) VALUES (?,?,?,?,?,?)",
                c.getComplaintNumber(), c.getUserId(), c.getSubject(), c.getCategory(), c.getDescription(), c.getStatus()
        );
    }

    public int assignStaff(int complaintId, Integer staffId) {
        return base.jdbc().update( "UPDATE complaints SET staff_id = ?, status = ? WHERE complaint_id = ?",
                staffId,
                "ASSIGNED",
                complaintId);
    }

    public int updateStatus(int complaintId, String status, String notes) {
        if ("Resolved".equalsIgnoreCase(status)) {
            return base.jdbc().update("UPDATE complaints SET status = ?, resolution_notes = ?, date_resolved = CURRENT_DATE WHERE complaint_id = ?",
                    status, notes, complaintId);
        } else {
            return base.jdbc().update("UPDATE complaints SET status = ?, resolution_notes = ? WHERE complaint_id = ?",
                    status, notes, complaintId);
        }
    }
}
