package com.example.Medicine.Project.Repository;

import com.example.Medicine.Project.Entity.PatientMedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientMedRepo extends JpaRepository<PatientMedEntity,Integer> {
}
