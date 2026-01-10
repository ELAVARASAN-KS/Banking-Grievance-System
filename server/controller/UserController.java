package com.grievance.controller;

import com.grievance.entity.Grievance;
import com.grievance.entity.User;
import com.grievance.service.FeedbackService;
import com.grievance.service.GrievanceService;
import com.grievance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private GrievanceService grievanceService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private com.grievance.service.UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String status, Model model) {
        User user = securityUtil.getCurrentUser();
        List<Grievance> grievances;
        
        if (status != null && !status.isEmpty()) {
            grievances = grievanceService.getUserGrievancesByStatus(user, Grievance.Status.valueOf(status));
        } else {
            grievances = grievanceService.getUserGrievances(user);
        }
        
        long totalGrievances = grievanceService.getUserGrievances(user).size();
        long pendingGrievances = grievances.stream()
                .filter(g -> g.getStatus() == Grievance.Status.PENDING).count();
        long resolvedGrievances = grievances.stream()
                .filter(g -> g.getStatus() == Grievance.Status.RESOLVED).count();
        
        model.addAttribute("user", user);
        model.addAttribute("totalGrievances", totalGrievances);
        model.addAttribute("pendingGrievances", pendingGrievances);
        model.addAttribute("resolvedGrievances", resolvedGrievances);
        model.addAttribute("grievances", grievances);
        model.addAttribute("selectedStatus", status != null ? status : "");
        
        return "user/dashboard";
    }

    @GetMapping("/raise-complaint")
    public String raiseComplaintPage(Model model) {
        model.addAttribute("categories", List.of("Account Issue",
                "Transaction Issue",
                "ATM Issue",
                "Card Issue",
                "Customer Service",
                "Others"));
        return "user/raise-complaint";
    }

    @PostMapping("/raise-complaint")
    public String raiseComplaint(@RequestParam String category,
                                @RequestParam String title,
                                @RequestParam String description,
                                RedirectAttributes redirectAttributes) {
        try {
            User user = securityUtil.getCurrentUser();
            Grievance grievance = grievanceService.createGrievance(user, category, title, description);
            redirectAttributes.addFlashAttribute("message", "Complaint raised successfully! Your complaint ID is: " + grievance.getId());
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to raise complaint: " + e.getMessage());
            return "redirect:/user/raise-complaint";
        }
    }

    @GetMapping("/track-complaint")
    public String trackComplaintPage() {
        return "user/track-complaint";
    }

    @PostMapping("/track-complaint")
    public String trackComplaint(@RequestParam Long complaintId, Model model) {
        try {
            Grievance grievance = grievanceService.findById(complaintId)
                    .orElseThrow(() -> new RuntimeException("Complaint not found"));
            
            User user = securityUtil.getCurrentUser();
            if (!grievance.getUser().getId().equals(user.getId())) {
                model.addAttribute("error", "You don't have access to this complaint");
                return "user/track-complaint";
            }
            
            boolean hasFeedback = feedbackService.hasFeedback(grievance);
            model.addAttribute("grievance", grievance);
            model.addAttribute("hasFeedback", hasFeedback);
            return "user/track-complaint";
        } catch (Exception e) {
            model.addAttribute("error", "Complaint not found: " + e.getMessage());
            return "user/track-complaint";
        }
    }

    @GetMapping("/feedback/{id}")
    public String feedbackPage(@PathVariable Long id, Model model) {
        try {
            Grievance grievance = grievanceService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Grievance not found"));
            
            User user = securityUtil.getCurrentUser();
            if (!grievance.getUser().getId().equals(user.getId())) {
                return "redirect:/user/dashboard";
            }
            
            if (grievance.getStatus() != Grievance.Status.RESOLVED) {
                model.addAttribute("error", "Grievance is not yet resolved");
                return "user/track-complaint";
            }
            
            if (feedbackService.hasFeedback(grievance)) {
                model.addAttribute("error", "Feedback already submitted");
                return "user/track-complaint";
            }
            
            model.addAttribute("grievance", grievance);
            return "user/feedback";
        } catch (Exception e) {
            return "redirect:/user/dashboard";
        }
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam Long grievanceId,
                               @RequestParam Integer rating,
                               @RequestParam(required = false) String comments,
                               RedirectAttributes redirectAttributes) {
        try {
            Grievance grievance = grievanceService.findById(grievanceId)
                    .orElseThrow(() -> new RuntimeException("Grievance not found"));
            
            feedbackService.submitFeedback(grievance, rating, comments);
            redirectAttributes.addFlashAttribute("message", "Feedback submitted successfully!");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to submit feedback: " + e.getMessage());
            return "redirect:/user/feedback/" + grievanceId;
        }
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        User user = securityUtil.getCurrentUser();
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New password and confirm password do not match!");
                return "redirect:/user/profile";
            }
            
            User user = securityUtil.getCurrentUser();
            // In a real application, verify current password before changing
            userService.updatePassword(user, newPassword);
            redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to change password: " + e.getMessage());
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String name,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = securityUtil.getCurrentUser();
            user.setName(name);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}

