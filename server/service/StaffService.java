package com.grievance.service;

import com.grievance.entity.Staff;
import com.grievance.entity.User;
import com.grievance.repository.StaffRepository;
import com.grievance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Staff addStaff(String name, String email, String workName) {
        if (staffRepository.existsByEmail(email)) {
            throw new RuntimeException("Staff with this email already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }

        // Create User account for staff
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("staff123")); // Default password
        user.setRole(User.Role.STAFF);
        userRepository.save(user);

        // Create Staff record
        Staff staff = new Staff();
        staff.setName(name);
        staff.setEmail(email);
        staff.setWorkName(workName);

        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    public void deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        
        // Also delete the associated user account
        userRepository.findByEmail(staff.getEmail())
                .ifPresent(user -> userRepository.delete(user));
        
        staffRepository.deleteById(id);
    }
}

