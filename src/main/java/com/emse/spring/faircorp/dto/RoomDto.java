package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RoomDto {
    private Long id;
    private Integer floor;
//    private Set<Window> windows;
//    private Set<Heater> heaters;
    private String name;
    private Double currentTemperature;
    private Double targetTemperature;
//    private Long roomId;

    public RoomDto() {
    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
//        this.windows = room.getWindows();
//        this.heaters = room.getHeaters();
        this.currentTemperature = room.getCurrentTemperature();
        this.targetTemperature = room.getTargetTemperature();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

//    public Set<Window> getWindows() {
//        return windows;
//    }
//
//    public void setWindows(Set<Window> windows) {
//        this.windows = windows;
//    }
//
//    public Set<Heater> getHeaters() {
//        return heaters;
//    }
//
//    public void setHeaters(Set<Heater> heaters) {
//        this.heaters = heaters;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

//    public Long getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(Long roomId) {
//        this.roomId = roomId;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RoomDto entity = (RoomDto) o;
//        return Objects.equals(this.id, entity.id) &&
//                Objects.equals(this.name, entity.name) &&
//                Objects.equals(this.roomStatus, entity.roomStatus);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, roomStatus);
//    }
//
//    @Override
//    public String toString() {
//        return getClass().getSimpleName() + "(" +
//                "id = " + id + ", " +
//                "name = " + name + ", " +
//                "roomStatus = " + roomStatus + ")";
//    }
}
