package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomDao extends JpaRepository<Room, Long>, RoomDaoCustom {

    @Query("SELECT r FROM Room r WHERE r.building.id=:building_id")
    List<Room> findByBuildingId(@Param("building_id") Long building_id);
}