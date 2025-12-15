package com.banking.grievance.service;

import com.banking.grievance.dao.ComplaintDao;
import com.banking.grievance.model.Complaint;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class ComplaintService {

    private final ComplaintDao dao;

    public ComplaintService(ComplaintDao dao) {
        this.dao = dao;
    }

    // Generate GR-XXXX
    private String generateComplaintNumber() {
        Random r = new Random();
        return "GR-" + (1000 + r.nextInt(9000));
    }

    // CREATE COMPLAINT ------------------------
    public Complaint createComplaint(Complaint c) {
        c.setComplaintNumber(generateComplaintNumber());
        c.setStatus("Pending");
        dao.create(c);
        return c;
    }

    // GETTERS ------------------------
    public List<Complaint> getAllComplaints() {
        return dao.findAll();
    }

    public Complaint getById(int id) {
        return dao.findById(id);
    }

    public Complaint getByNumber(String num) {
        return dao.findByNumber(num);
    }

    public List<Complaint> getByUserId(int uid) {
        return dao.findByUserId(uid);
    }

    public List<Complaint> getByStaffId(int sid) {
        return dao.findByStaffId(sid);
    }

    // ASSIGN STAFF ------------------------
    public void assignStaff(int complaintId, Integer staffId) {
        int updated = dao.assignStaff(complaintId, staffId);

        if (updated == 0) {
            throw new RuntimeException("COMPLAINT_NOT_FOUND");
        }
    }
    // UPDATE STATUS ------------------------
    public void updateStatus(int complaintId, String status, String notes) {
        dao.updateStatus(complaintId, status, notes);
    }

}


