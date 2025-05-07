package com.andriokar.repositories;

import com.andriokar.entities.SoftwareEngineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
}
