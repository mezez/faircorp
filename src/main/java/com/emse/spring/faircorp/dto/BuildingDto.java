package com.emse.spring.faircorp.dto;

import com.emse.spring.faircorp.model.Building;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
public class BuildingDto implements Serializable {
    private Long id;
    private String name;
    private Integer numberOfFloors;
    private Integer numberOfRooms;
    private Set<RoomDto> room;


    public BuildingDto() {
    }

    public BuildingDto(Building building) {
        this.id = building.getId();
        this.name = building.getName();
        this.numberOfFloors = building.getNumberOfFloors();
        this.numberOfRooms = building.getNumberOfRooms();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Set<RoomDto> getRoom() {
        return room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name +  ")";
    }
}
