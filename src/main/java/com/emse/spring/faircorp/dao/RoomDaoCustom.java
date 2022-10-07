package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import java.util.List;

public interface RoomDaoCustom {
    List<Room> findRoomsByName(String name);
//    List<Room> findByBuildingId(Long building_id);
}
