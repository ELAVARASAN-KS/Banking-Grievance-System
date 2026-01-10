package com.grievance.service;

import com.grievance.entity.Grievance;
import com.grievance.entity.User;
import com.grievance.entity.Staff;
import com.grievance.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GrievanceService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    public Grievance createGrievance(User user, String category, String title, String description) {
        Grievance grievance = new Grievance();
        grievance.setUser(user);
        grievance.setCategory(category);
        grievance.setTitle(title);
        grievance.setDescription(description);
        grievance.setStatus(Grievance.Status.PENDING);

        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getUserGrievances(User user) {
        return grievanceRepository.findByUser(user);
    }

    public Optional<Grievance> findById(Long id) {
        return grievanceRepository.findById(id);
    }

    public List<Grievance> getAllGrievances() {
        return grievanceRepository.findAll();
    }

    public List<Grievance> getGrievancesByStatus(Grievance.Status status) {
        return grievanceRepository.findByStatus(status);
    }

    public List<Grievance> getGrievancesByCategory(String category) {
        return grievanceRepository.findByCategory(category);
    }

    public List<Grievance> getGrievancesByStatusAndCategory(Grievance.Status status, String category) {
        return grievanceRepository.findByStatusAndCategory(status, category);
    }

    public Grievance assignGrievance(Long grievanceId, Staff staff) {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));
        
        grievance.setAssignedStaff(staff);
        grievance.setStatus(Grievance.Status.ASSIGNED);
        
        return grievanceRepository.save(grievance);
    }

    public List<Grievance> getStaffGrievances(Staff staff) {
        return grievanceRepository.findByAssignedStaff(staff);
    }

    public Grievance updateGrievanceStatus(Long grievanceId, Grievance.Status status) {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));
        
        grievance.setStatus(status);
        if (status == Grievance.Status.RESOLVED) {
            grievance.setResolvedDate(java.time.LocalDateTime.now());
        }
        
        return grievanceRepository.save(grievance);
    }

    public long getTotalGrievances() {
        return grievanceRepository.count();
    }

    public List<Grievance> getUserGrievancesByStatus(User user, Grievance.Status status) {
        return grievanceRepository.findByUserAndStatus(user, status);
    }

    public List<Grievance> getStaffGrievancesByStatus(Staff staff, Grievance.Status status) {
        return grievanceRepository.findByAssignedStaffAndStatus(staff, status);
    }
}

