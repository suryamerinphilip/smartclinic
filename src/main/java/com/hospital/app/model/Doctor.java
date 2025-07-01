package com.hospital.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialty;
    @NotNull
    @Size(min = 3, max = 100)
    @Email
    private String email;
    private String password;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    @ElementCollection
    private List<String> availableTimes;
}
