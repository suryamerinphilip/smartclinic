package com.hospital.app.repo;

import com.hospital.app.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
    // Find prescriptions associated with a specific appointment
    List<Prescription> findByAppointmentId(Long appointmentId);

}
