package com.grievance.controller;

import com.grievance.entity.Grievance;
import com.grievance.entity.Staff;
import com.grievance.service.GrievanceService;
import com.grievance.service.StaffService;
import com.grievance.service.StaffNoteService;
import com.grievance.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private GrievanceService grievanceService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffNoteService staffNoteService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String status,
                           @RequestParam(required = false) String category,
                           Model model) {
        long totalGrievances = grievanceService.getTotalGrievances();
        long pendingGrievances = grievanceService.getGrievancesByStatus(Grievance.Status.PENDING).size();
        long assignedGrievances = grievanceService.getGrievancesByStatus(Grievance.Status.ASSIGNED).size();
        long inProgressGrievances = grievanceService.getGrievancesByStatus(Grievance.Status.IN_PROGRESS).size();
        long resolvedGrievances = grievanceService.getGrievancesByStatus(Grievance.Status.RESOLVED).size();
        
        List<Grievance> allGrievances;
        if (status != null && !status.isEmpty() && category != null && !category.isEmpty()) {
            allGrievances = grievanceService.getGrievancesByStatusAndCategory(
                Grievance.Status.valueOf(status), category);
        } else if (status != null && !status.isEmpty()) {
            allGrievances = grievanceService.getGrievancesByStatus(Grievance.Status.valueOf(status));
        } else if (category != null && !category.isEmpty()) {
            allGrievances = grievanceService.getGrievancesByCategory(category);
        } else {
            allGrievances = grievanceService.getAllGrievances();
        }
        
        List<Staff> allStaff = staffService.getAllStaff();
        List<String> categories = Arrays.asList("Account Issue",
                "Transaction Issue",
                "ATM Issue",
                "Card Issue",
                "Customer Service",
                "Others");
        
        model.addAttribute("totalGrievances", totalGrievances);
        model.addAttribute("pendingGrievances", pendingGrievances);
        model.addAttribute("assignedGrievances", assignedGrievances);
        model.addAttribute("inProgressGrievances", inProgressGrievances);
        model.addAttribute("resolvedGrievances", resolvedGrievances);
        model.addAttribute("grievances", allGrievances);
        model.addAttribute("staffList", allStaff);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedStatus", status != null ? status : "");
        model.addAttribute("selectedCategory", category != null ? category : "");
        
        return "admin/dashboard";
    }

    @PostMapping("/assign-grievance")
    public String assignGrievance(@RequestParam Long grievanceId,
                                 @RequestParam Long staffId,
                                 RedirectAttributes redirectAttributes) {
        try {
            Staff staff = staffService.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Staff not found"));
            
            grievanceService.assignGrievance(grievanceId, staff);
            redirectAttributes.addFlashAttribute("message", "Grievance assigned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to assign grievance: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/add-staff")
    public String addStaffPage() {
        return "admin/add-staff";
    }

    @PostMapping("/add-staff")
    public String addStaff(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String workName,
                          RedirectAttributes redirectAttributes) {
        try {
            staffService.addStaff(name, email, workName);
            redirectAttributes.addFlashAttribute("message", "Staff added successfully!");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add staff: " + e.getMessage());
            return "redirect:/admin/add-staff";
        }
    }

    @GetMapping("/manage-staff")
    public String manageStaff(Model model) {
        List<Staff> allStaff = staffService.getAllStaff();
        model.addAttribute("staffList", allStaff);
        return "admin/manage-staff";
    }

    @PostMapping("/delete-staff/{id}")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            staffService.deleteStaff(id);
            redirectAttributes.addFlashAttribute("message", "Staff deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete staff: " + e.getMessage());
        }
        return "redirect:/admin/manage-staff";
    }

    @GetMapping("/grievance-details/{id}")
    public String grievanceDetails(@PathVariable Long id, Model model) {
        Grievance grievance = grievanceService.findById(id)
                .orElseThrow(() -> new RuntimeException("Grievance not found"));
        
        var notes = staffNoteService.getNotesByGrievance(grievance);
        var feedback = feedbackService.getFeedbackByGrievance(grievance);
        
        model.addAttribute("grievance", grievance);
        model.addAttribute("notes", notes);
        model.addAttribute("feedback", feedback.orElse(null));
        
        return "admin/grievance-details";
    }
}

