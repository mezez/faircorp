package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingDao extends JpaRepository<Building, Long> {
    public List<Building> findByName(String buildingName);
}