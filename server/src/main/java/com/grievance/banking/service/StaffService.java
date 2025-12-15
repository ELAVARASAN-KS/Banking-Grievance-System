package com.banking.grievance.service;

import com.banking.grievance.dao.StaffDao;
import com.banking.grievance.model.Staff;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StaffService {

    private final StaffDao dao;
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public StaffService(StaffDao dao) {
        this.dao = dao;
    }

    public List<Staff> getAllStaff() {
        return dao.findAll();
    }

    public Staff getStaffById(int id) {
        return dao.findById(id);
    }

    public List<Staff> getStaffByDepartment(String dept) {
        return dao.findByDepartment(dept);
    }

    public boolean addStaff(Staff staff) {
        // hash password
        staff.setPassword(bcrypt.encode(staff.getPassword()));
        return dao.create(staff) > 0;
    }

    public boolean resolveComplaint(int complaintId, int staffId, String note) {
        return dao.resolveComplaint(complaintId, staffId, note);
    }


}
