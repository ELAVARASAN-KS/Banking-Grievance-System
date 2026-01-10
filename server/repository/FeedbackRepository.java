package com.grievance.repository;

import com.grievance.entity.Feedback;
import com.grievance.entity.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByGrievance(Grievance grievance);
    boolean existsByGrievance(Grievance grievance);
}




