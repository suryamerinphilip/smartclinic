package com.hospital.app.service;

import com.hospital.app.model.Doctor;
import com.hospital.app.repo.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RestController
@RequestMapping("/doctors")
public class DoctorControllerService {

    private final Map<Long, List<Appointment>> appointmentData = new HashMap<>();
    private final Map<String, Doctor> doctorData = new HashMap<>();
    private final Map<String, String> tokenStore = new HashMap<>();

    public DoctorControllerService() {
        // Sample data init (replace with DB or Repository calls)
        doctorData.put("doc@example.com", new Doctor(1L, "doc@example.com", BCrypt.hashpw("secret", BCrypt.gensalt())));
        tokenStore.put("user|validToken123", "VALID");
    }

    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        if (!isValidToken(user, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token or unauthorized access"));
        }

        List<LocalTime> availableSlots = getAvailability(doctorId, date);
        return ResponseEntity.ok(Map.of(
                "doctorId", doctorId,
                "date", date.toString(),
                "availableSlots", availableSlots
        ));
    }

    public boolean isValidToken(String user, String token) {
        return "VALID".equals(tokenStore.get(user + "|" + token));
    }

    public List<LocalTime> getAvailability(Long doctorId, LocalDate date) {
        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(9, 0);
        while (!time.isAfter(LocalTime.of(17, 0))) {
            allSlots.add(time);
            time = time.plusMinutes(30);
        }

        List<Appointment> booked = appointmentData.getOrDefault(doctorId, List.of());
        Set<LocalTime> bookedTimes = booked.stream()
                .filter(app -> app.getDate().toLocalDate().equals(date))
                .map(app -> app.getDate().toLocalTime())
                .collect(Collectors.toSet());

        return allSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot))
                .collect(Collectors.toList());
    }

    public boolean validateDoctorLogin(String email, String password) {
        Doctor doctor = doctorData.get(email);
        return doctor != null && BCrypt.checkpw(password, doctor.hashedPassword);
    }

    // Sample inner classes (replace with actual entities)
    static class Doctor {
        Long id;
        String email;
        String hashedPassword;
        Doctor(Long id, String email, String hashedPassword) {
            this.id = id; this.email = email; this.hashedPassword = hashedPassword;
        }
    }

    static class Appointment {
        Long doctorId;
        LocalDateTime date;
        Appointment(Long doctorId, LocalDateTime date) {
            this.doctorId = doctorId; this.date = date;
        }
        public LocalDateTime getDate() { return date; }
    }
}
