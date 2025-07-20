package com.example.Medicine.Project.Repository;


import com.example.Medicine.Project.Entity.ContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactusRepository extends JpaRepository<ContactUsEntity,Long> {

}
