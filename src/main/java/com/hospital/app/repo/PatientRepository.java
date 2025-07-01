package com.hospital.app.repo;

import com.hospital.app.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository  extends JpaRepository<Patient,Long> {
    // Find patient by exact email
    Patient findByEmail(String email);

    // Find patient by either email or phone number
    Patient findByEmailOrPhone(String email, String phone);


}
