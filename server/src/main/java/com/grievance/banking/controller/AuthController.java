package com.banking.grievance.controller;

import com.banking.grievance.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")   // ✅ FIXED (no double /api)
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /* ================= USER REGISTER ================= */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.registerUser(
                        body.get("fullName"),
                        body.get("email"),
                        body.get("mobileNumber"),
                        body.get("password"),
                        body.get("accountId")
                )
        );
    }

    /* ================= USER LOGIN ================= */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.userLogin(
                        body.get("email"),
                        body.get("password")
                )
        );
    }

    /* ================= ADMIN LOGIN ================= */
    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.adminLogin(
                        body.get("username"),
                        body.get("password")
                )
        );
    }

    /* ================= STAFF LOGIN ================= */
    @PostMapping("/staff/login")
    public ResponseEntity<String> staffLogin(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.staffLogin(
                        body.get("email"),
                        body.get("password")
                )
        );
    }
}
