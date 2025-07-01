package com.hospital.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
    public class DashboardController {
        @Autowired
        private TokenValidationService tokenValidationService;


        // admin dashboard method
        @GetMapping("/adminDashboard/{token}")
        public String adminDashboard(@PathVariable String token) {
            Map<String, Object> result = tokenValidationService.validateToken(token, "admin");

            if (result.isEmpty()) {
                return "admin/adminDashboard"; // Thymeleaf will resolve to adminDashboard.html
            } else {
                return "redirect:/";
            }
        }

        // doctor dashboard method
        @GetMapping("/doctorDashboard/{token}")
        public String doctorDashboard(@PathVariable String token) {
            Map<String, Object> result = tokenValidationService.validateToken(token, "doctor");

            if (result.isEmpty()) {
                return "doctor/doctorDashboard";
            } else {
                return "redirect:/";
            }
        }

