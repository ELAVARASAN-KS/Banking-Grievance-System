package com.grievance.repository;

import com.grievance.entity.StaffNote;
import com.grievance.entity.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffNoteRepository extends JpaRepository<StaffNote, Long> {
    List<StaffNote> findByGrievance(Grievance grievance);
    Optional<StaffNote> findFirstByGrievanceOrderByUpdatedDateDesc(Grievance grievance);
}




