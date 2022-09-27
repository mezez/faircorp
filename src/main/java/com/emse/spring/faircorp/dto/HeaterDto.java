package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Heater;

import java.util.Objects;

public class HeaterDto {
    private Long id;
    private String name;
    private Long power;
    private HeaterStatus status;
    private Long roomId;

    public HeaterDto() {
    }

    public HeaterDto(Heater heater) {
        this.id = heater.getId();
        this.name = heater.getName();
        this.status = heater.getHeaterStatus();
        this.power = heater.getPower();
        this.roomId = heater.getRoom().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeaterStatus getHeaterStatus() {
        return status;
    }

    public void setHeaterStatus(HeaterStatus status) {
        this.status = status;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaterDto entity = (HeaterDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "status = " + status + ")";
    }
}
