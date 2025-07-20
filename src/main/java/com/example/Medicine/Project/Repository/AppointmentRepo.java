package com.example.Medicine.Project.Repository;

import com.example.Medicine.Project.Entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentEntity,Long> {
}
