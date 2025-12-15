package com.banking.grievance.service;

import com.banking.grievance.dao.UserDao;
import com.banking.grievance.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public User getUserById(int id) {
        return dao.findById(id);
    }
}
