package com.banking.grievance.controller;

import com.banking.grievance.model.Staff;
import com.banking.grievance.service.StaffService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin("*")
public class StaffController {

    private final StaffService service;

    public StaffController(StaffService service) {
        this.service = service;
    }

    // GET ALL STAFF -------------------------------------------
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(service.getAllStaff());
    }

    // GET STAFF BY DEPARTMENT ---------------------------------
    @GetMapping("/department/{dept}")
    public ResponseEntity<List<Staff>> getStaffByDept(@PathVariable String dept) {
        return ResponseEntity.ok(service.getStaffByDepartment(dept));
    }

    // GET STAFF BY ID -----------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable int id) {
        Staff s = service.getStaffById(id);
        if (s == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("success", false, "message", "Staff not found"));
        }
        return ResponseEntity.ok(s);
    }

    // ADD STAFF MEMBER ----------------------------------------
    @PostMapping
    public ResponseEntity<?> addStaff(@RequestBody Staff staff) {
        boolean ok = service.addStaff(staff);
        if (!ok) {
            return ResponseEntity.status(400)
                    .body(Map.of("success", false, "message", "Failed to add staff"));
        }
        return ResponseEntity.ok(Map.of("success", true, "message", "Staff added"));
    }

    @PostMapping("/resolve")
    public ResponseEntity<?> resolveComplaint(@RequestBody Map<String, Object> req) {

        int complaintId = (int) req.get("complaintId");
        int staffId = (int) req.get("staffId");
        String note = req.get("note").toString();

        boolean ok = service.resolveComplaint(complaintId, staffId, note);

        if (!ok) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Resolve failed"));
        }

        return ResponseEntity.ok(
                Map.of("success", true, "message", "Complaint resolved and note added")
        );
    }
}
