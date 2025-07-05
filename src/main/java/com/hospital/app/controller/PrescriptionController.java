package com.hospital.app.controller;

import ch.qos.logback.core.model.Model;
import com.hospital.app.model.Prescription;
import com.hospital.app.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PrescriptionController {
    @GetMapping("/Prescribe/save")
    public String listPrescriptions(Model model) {
        PrescriptionService prescriptionService;
        model.addAttribute("prescriptions", prescriptionService.getAllPrescriptions());
        return "prescription/list"; // points to templates/prescription/list.html
    }

    // Show form to create a new prescription
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        return "prescription/form"; // templates/prescription/form.html
    }

    // Handle form submission
    @PostMapping
    public String savePrescription(@ModelAttribute Prescription prescription) {
        PrescriptionController prescriptionService;
        prescriptionService.savePrescription(prescription);
        return "redirect:/prescriptions";
    }

    // Delete a prescription by ID
    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/prescriptions";
    }


}
