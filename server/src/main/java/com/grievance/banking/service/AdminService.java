package com.banking.grievance.service;

import com.banking.grievance.dao.AdminDao;
import com.banking.grievance.model.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminDao dao;

    public AdminService(AdminDao dao) {
        this.dao = dao;
    }

    public Admin getAdminByUsername(String username) {
        return dao.findByUsername(username);
    }
}
