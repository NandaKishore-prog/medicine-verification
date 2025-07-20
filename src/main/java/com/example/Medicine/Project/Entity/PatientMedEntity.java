package com.example.Medicine.Project.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PatientMedEntity{
    @Id
    private int code;

    private String name;
    private int age;
    private String gender;
    private String diagnosis;
    private String number;
    private String address;
    private String medications;
    private String doctorNotes;
}