package com.hospital.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prescriptions")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Size(min = 3, max = 20)
    private String patientName;

    private Long appointmentId;
    private String medication;
    @Size(min = 3, max = 100)
    private String dosage;
    @Size(max = 200)
    private String doctorNotes;





}
