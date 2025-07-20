package com.example.Medicine.Project.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientMedDTO {
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