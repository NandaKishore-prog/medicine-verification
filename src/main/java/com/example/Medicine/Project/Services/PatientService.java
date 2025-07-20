package com.example.Medicine.Project.Services;


import com.example.Medicine.Project.Entity.AppointmentEntity;
import com.example.Medicine.Project.Entity.ContactUsEntity;
import com.example.Medicine.Project.Entity.PatientMedEntity;
import com.example.Medicine.Project.Model.PatientMedDTO;
import com.example.Medicine.Project.Repository.AppointmentRepo;
import com.example.Medicine.Project.Repository.ContactusRepository;
import com.example.Medicine.Project.Repository.PatientMedRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientMedRepo patientMedRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ContactusRepository contactusRepository;
    @Autowired
    AppointmentRepo appointmentRepo;

    public void SavePatientMed(PatientMedDTO patientMedDTO){
     patientMedRepo.save(modelMapper.map(patientMedDTO, PatientMedEntity.class));

    }


    public void SaveContactUs(ContactUsEntity contactUsEntity){
        contactusRepository.save(contactUsEntity);
    }

    public void SaveAppointment(AppointmentEntity appointment) {
        appointmentRepo.save(appointment);
    }
}
