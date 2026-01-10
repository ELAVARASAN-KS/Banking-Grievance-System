package com.grievance.repository;

import com.grievance.entity.Grievance;
import com.grievance.entity.User;
import com.grievance.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    List<Grievance> findByUser(User user);
    List<Grievance> findByStatus(Grievance.Status status);
    List<Grievance> findByCategory(String category);
    List<Grievance> findByStatusAndCategory(Grievance.Status status, String category);
    List<Grievance> findByUserAndStatus(User user, Grievance.Status status);
    List<Grievance> findByAssignedStaff(Staff staff);
    List<Grievance> findByAssignedStaffAndStatus(Staff staff, Grievance.Status status);
}

