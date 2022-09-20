package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeaterDao extends JpaRepository<com.emse.spring.faircorp.model.Heater, Long> {
    @Modifying
    @Query("delete from Heater h where h.room.id=:room_id")
    int deleteByRoom(@Param("room_id") Long room_id);
}