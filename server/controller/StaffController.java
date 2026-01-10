package com.grievance.controller;

import com.grievance.entity.Grievance;
import com.grievance.entity.Staff;
import com.grievance.entity.User;
import com.grievance.repository.StaffRepository;
import com.grievance.service.GrievanceService;
import com.grievance.service.StaffNoteService;
import com.grievance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private GrievanceService grievanceService;

    @Autowired
    private StaffNoteService staffNoteService;

    @Autowired
    private StaffRepository staffRepository;

    private Staff getCurrentStaff() {
        User user = securityUtil.getCurrentUser();
        return staffRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    @Autowired
    private com.grievance.service.UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String status, Model model) {
        Staff staff = getCurrentStaff();
        List<Grievance> assignedGrievances;
        
        if (status != null && !status.isEmpty()) {
            assignedGrievances = grievanceService.getStaffGrievancesByStatus(staff, Grievance.Status.valueOf(status));
        } else {
            assignedGrievances = grievanceService.getStaffGrievances(staff);
        }
        
        List<Grievance> allAssigned = grievanceService.getStaffGrievances(staff);
        long totalAssigned = allAssigned.size();
        long pending = allAssigned.stream()
                .filter(g -> g.getStatus() == Grievance.Status.ASSIGNED).count();
        long inProgress = allAssigned.stream()
                .filter(g -> g.getStatus() == Grievance.Status.IN_PROGRESS).count();
        long resolved = allAssigned.stream()
                .filter(g -> g.getStatus() == Grievance.Status.RESOLVED).count();
        
        model.addAttribute("staff", staff);
        model.addAttribute("totalAssigned", totalAssigned);
        model.addAttribute("pending", pending);
        model.addAttribute("inProgress", inProgress);
        model.addAttribute("resolved", resolved);
        model.addAttribute("grievances", assignedGrievances);
        model.addAttribute("selectedStatus", status != null ? status : "");
        
        return "staff/dashboard";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long grievanceId,
                              @RequestParam String status,
                              @RequestParam(required = false) String notes,
                              RedirectAttributes redirectAttributes) {
        try {
            Staff staff = getCurrentStaff();
            Grievance grievance = grievanceService.findById(grievanceId)
                    .orElseThrow(() -> new RuntimeException("Grievance not found"));
            
            if (!grievance.getAssignedStaff().getId().equals(staff.getId())) {
                redirectAttributes.addFlashAttribute("error", "You don't have access to this grievance");
                return "redirect:/staff/dashboard";
            }
            
            Grievance.Status newStatus = Grievance.Status.valueOf(status);
            grievanceService.updateGrievanceStatus(grievanceId, newStatus);
            
            if (notes != null && !notes.trim().isEmpty()) {
                staffNoteService.addNote(grievance, staff, notes);
            }
            
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update status: " + e.getMessage());
        }
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "staff/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New password and confirm password do not match!");
                return "redirect:/staff/change-password";
            }
            
            User user = securityUtil.getCurrentUser();
            userService.updatePassword(user, newPassword);
            redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to change password: " + e.getMessage());
        }
        return "redirect:/staff/change-password";
    }
}

