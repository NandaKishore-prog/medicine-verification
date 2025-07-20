package com.example.Medicine.Project.Controller;

import com.example.Medicine.Project.Entity.AppointmentEntity;
import com.example.Medicine.Project.Entity.ContactUsEntity;
import com.example.Medicine.Project.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/contactus")
    public void SaveContactUs(@RequestBody ContactUsEntity contactUsEntity){
        patientService.SaveContactUs(contactUsEntity);
    }
    @PostMapping("/appointment")
    public void SaveAppointment(@RequestBody AppointmentEntity appointment){
        patientService.SaveAppointment(appointment);
    }
}
