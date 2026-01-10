package com.grievance.service;

import com.grievance.entity.StaffNote;
import com.grievance.entity.Grievance;
import com.grievance.entity.Staff;
import com.grievance.repository.StaffNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffNoteService {

    @Autowired
    private StaffNoteRepository staffNoteRepository;

    public StaffNote addNote(Grievance grievance, Staff staff, String notes) {
        StaffNote staffNote = new StaffNote();
        staffNote.setGrievance(grievance);
        staffNote.setStaff(staff);
        staffNote.setNotes(notes);

        return staffNoteRepository.save(staffNote);
    }

    public List<StaffNote> getNotesByGrievance(Grievance grievance) {
        return staffNoteRepository.findByGrievance(grievance);
    }
}




