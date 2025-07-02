# Schema Design – Clinic System

---

## patients

| Column           | Data Type     | Constraints                                                    |
|------------------|---------------|----------------------------------------------------------------|
| id               | BIGINT        | PRIMARY KEY, AUTO_INCREMENT, NOT NULL                          |
| name             | VARCHAR(100)  | NOT NULL                                                       |
| email            | VARCHAR(100)  | NOT NULL, UNIQUE                                               |
| password         | VARCHAR(255)  | NOT NULL (store hashed)                                        |
| phone            | VARCHAR(10)   | NOT NULL (10 digits, validate via code)                        |
| address          | VARCHAR(255)  | NULL                                                           |
| created_at       | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP                            |

- Deletion policy: use `ON DELETE RESTRICT` or soft-delete flag to preserve appointment history.

---

## doctors

| Column           | Data Type     | Constraints                                                    |
|------------------|---------------|----------------------------------------------------------------|
| id               | BIGINT        | PRIMARY KEY, AUTO_INCREMENT, NOT NULL                          |
| name             | VARCHAR(100)  | NOT NULL                                                       |
| specialty        | VARCHAR(100)  | NULL                                                           |
| email            | VARCHAR(100)  | NOT NULL, UNIQUE                                               |
| password         | VARCHAR(255)  | NOT NULL (store hashed)                                        |
| phone            | VARCHAR(10)   | NOT NULL (10 digits, validate via code)                        |
| created_at       | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP                            |

- Prevent double-booking: application-level check or unique index on `(doctor_id, appointment_start)`.

---

## appointments

| Column             | Data Type     | Constraints                                                               |
|--------------------|---------------|---------------------------------------------------------------------------|
| id                 | BIGINT        | PRIMARY KEY, AUTO_INCREMENT, NOT NULL                                     |
| patient_id         | BIGINT        | NOT NULL, FOREIGN KEY → patients(id) ON DELETE RESTRICT                   |
| doctor_id          | BIGINT        | NOT NULL, FOREIGN KEY → doctors(id) ON DELETE RESTRICT                    |
| appointment_time   | DATETIME      | NOT NULL, must be in the future (validated by `@Future`)                  |
| status             | TINYINT       | NOT NULL, default 0 (e.g., 0=SCHEDULED, 1=COMPLETED, 2=CANCELLED)         |
| created_at         | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP                                       |

- Constraint: `appointment_time` must be unique per doctor to avoid overlaps.  
- Consider adding `appointment_end` (e.g., `appointment_time + 1 hour`) and a span-overlap check at the application level.

---

## admin

| Column           | Data Type     | Constraints                                                    |
|------------------|---------------|----------------------------------------------------------------|
| id               | BIGINT        | PRIMARY KEY, AUTO_INCREMENT, NOT NULL                          |
| username         | VARCHAR(50)   | NOT NULL, UNIQUE                                               |
| password         | VARCHAR(255)  | NOT NULL, WRITE_ONLY (hashed, via `@JsonProperty`)             |
| created_at       | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP                            |

- Roles & permissions managed in application code or via a separate roles table.

---

## prescription

| Column           | Data Type     | Constraints                                                    |
|------------------|---------------|----------------------------------------------------------------|
| id               | BIGINT        | PRIMARY KEY, AUTO_INCREMENT, NOT NULL                          |
| patient_id       | BIGINT        | NOT NULL, FOREIGN KEY → patients(id) ON DELETE CASCADE         |
| appointment_id   | BIGINT        | NOT NULL, FOREIGN KEY → appointments(id) ON DELETE CASCADE     |
| medication       | VARCHAR(100)  | NOT NULL                                                       |
| dosage           | VARCHAR(100)  | NOT NULL                                                       |
| doctor_notes     | VARCHAR(200)  | NULL                                                           |
| created_at       | TIMESTAMP     | NOT NULL, DEFAULT CURRENT_TIMESTAMP                            |

- Link prescriptions to both patient and appointment for traceability.

---

## Relationships & Constraints

- One patient → many appointments (restrict deletion).  
- One doctor → many appointments (restrict deletion, no overlaps).  
- One appointment → many prescriptions (cascade delete).  
- Validate emails and phones via application code annotations (`@Email`, `@Pattern`).  
- Consider soft deletes (`is_active` flags) for audit trails.

---
                               +---------------+
                               |     Admin     |
                               +---------------+
                               | PK id         |
                               | username      |
                               | password_hash |
                               +---------------+

    +---------------+                +---------------+                +------------------+
    |   Patients    |                |    Doctors    |                | Clinic Locations |
    +---------------+                +---------------+                +------------------+
    | PK id         |                | PK id         |                | PK id            |
    | name          |                | name          |                | name             |
    | email         |                | specialty     |                | address_line1    |
    | phone         |                | email         |                | city             |
    +---------------+                | phone         |                | state            |
             | 1                      +---------------+                +------------------+
             | M                            | 1                                |
             |                              | M                                | M
             |                              |                                  |
             |                              |                                  |
             |           +-----------------------------------+              |
             |           |           Appointments            |<-------------+
             |           +-----------------------------------+
             |           | PK id                             |
             +---------->| FK patient_id → Patients(id)      |
                         | FK doctor_id  → Doctors(id)       |
                         | FK location_id → Clinic Locations(id) |
                         | appointment_time                   |
                         | status                             |
                         +-----------------------------------+
                              | 1           | 1
                              |             |
                              |             |
                              | M           | 1
              +---------------+             +---------------+
              |                                             |
              v                                             v
    +-----------------+                            +------------------+
    |    Payments     |                            |  Prescriptions   |
    +-----------------+                            +------------------+
    | PK id           |                            | PK id            |
    | FK appointment_id → Appointments(id)        | FK appointment_id → Appointments(id) |
    | amount          |                            | medication       |
    | payment_date    |                            | dosage           |
    +-----------------+                             | doctor_notes     |
                                                   +------------------+
