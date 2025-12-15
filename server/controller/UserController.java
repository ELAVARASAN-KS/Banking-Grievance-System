package com.banking.grievance.controller;

import com.banking.grievance.model.User;
import com.banking.grievance.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET USER BY ID ------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        User user = service.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(404)
                    .body(java.util.Map.of("success", false, "message", "User not found"));
        }
        return ResponseEntity.ok(user);
    }
}
