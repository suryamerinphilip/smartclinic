package com.hospital.app.controller;

import com.hospital.app.model.Doctor;
import com.hospital.app.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    // Constructor Injection
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // GET all doctors
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // GET doctor by ID

    // POST new doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    // PUT update doctor
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        // ✅ Step 1: Validate token
        if (!tokenService.isValidForUser(user, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token or unauthorized access"));
        }

        // ✅ Step 2: Fetch availability
        List<LocalTime> availableSlots = doctorService.getAvailability(doctorId, date);

        // ✅ Step 3: Build structured response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("doctorId", doctorId);
        response.put("date", date.toString());
        response.put("availableSlots", availableSlots);

        return ResponseEntity.ok(response);
    }


    // DELETE doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        if (doctorService.deleteDoctor(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
