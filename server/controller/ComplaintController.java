package com.banking.grievance.controller;

import com.banking.grievance.model.Complaint;
import com.banking.grievance.service.ComplaintService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*")
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    // CREATE COMPLAINT ----------------------------------------
    @PostMapping
    public ResponseEntity<?> createComplaint(@RequestBody Complaint c) {
        Complaint created = service.createComplaint(c);
        return ResponseEntity.ok(
                Map.of("success", true,
                        "complaintNumber", created.getComplaintNumber(),
                        "complaint", created));
    }

    // GET ALL COMPLAINTS --------------------------------------
    @GetMapping
    public ResponseEntity<List<Complaint>> getAll() {
        return ResponseEntity.ok(service.getAllComplaints());
    }

    // GET BY ID -----------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaintById(@PathVariable int id) {
        Complaint c = service.getById(id);
        if (c == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("success", false, "message", "Complaint not found"));
        }
        return ResponseEntity.ok(c);
    }

    // GET BY COMPLAINT NUMBER ---------------------------------
    @GetMapping("/number/{num}")
    public ResponseEntity<?> getByNumber(@PathVariable String num) {
        Complaint c = service.getByNumber(num);
        if (c == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("success", false, "message", "Complaint not found"));
        }
        return ResponseEntity.ok(c);
    }

    // GET BY USER ID ------------------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> byUser(@PathVariable int userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    // GET BY STAFF ID -----------------------------------------
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Complaint>> byStaff(@PathVariable int staffId) {
        return ResponseEntity.ok(service.getByStaffId(staffId));
    }

    // ASSIGN STAFF --------------------------------------------
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignStaff(@PathVariable int id,
                                         @RequestBody Map<String, Integer> body) {
        Integer staffId = body.get("staffId");
        service.assignStaff(id, staffId);
        return ResponseEntity.ok(Map.of("success", true, "message", "Staff assigned"));
    }

    // UPDATE STATUS -------------------------------------------
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable int id,
                                          @RequestBody Map<String, String> body) {
        String status = body.get("status");
        String notes = body.getOrDefault("resolutionNotes", "");

        service.updateStatus(id, status, notes);

        return ResponseEntity.ok(Map.of("success", true, "message", "Status updated"));
    }
}
