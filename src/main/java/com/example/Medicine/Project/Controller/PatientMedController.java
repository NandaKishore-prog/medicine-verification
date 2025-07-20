package com.example.Medicine.Project.Controller;


import com.example.Medicine.Project.Model.PatientMedDTO;
import com.example.Medicine.Project.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientMedController {

    @Autowired
    PatientService patientService;



    @PostMapping("/PatientMed")
    public String SavePatientMed(@ModelAttribute PatientMedDTO patientMedDTO){
        patientService.SavePatientMed(patientMedDTO);
        return "Successfully saved";
    }



}
