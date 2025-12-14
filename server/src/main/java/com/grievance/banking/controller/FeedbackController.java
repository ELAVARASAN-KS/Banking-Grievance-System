package com.banking.grievance.controller;

import com.banking.grievance.model.Feedback;
import com.banking.grievance.service.FeedbackService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin("*")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    // SUBMIT FEEDBACK -----------------------------------------
    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Feedback f) {
        boolean ok = service.submitFeedback(f);
        if (!ok) {
            return ResponseEntity.status(400)
                    .body(Map.of("success", false, "message", "Failed to submit feedback"));
        }
        return ResponseEntity.ok(Map.of("success", true, "message", "Feedback submitted"));
    }
}
