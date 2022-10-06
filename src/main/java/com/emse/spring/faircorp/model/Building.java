package com.emse.spring.faircorp.model;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private  String name;
    @NotNull
    private Integer numberOfFloors;

    @Nullable
    private Integer numberOfRooms;

    @OneToMany(mappedBy = "building")
    private Set<Room> room;

    public Building() {
    }

    public Building(String name, Integer numberOfFloors, @Nullable Integer numberOfRooms) {
        this.name = name;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRooms = numberOfRooms;
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

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    @Nullable
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(@Nullable Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Set<Room> getRoom() {
        return room;
    }

    public void setRoom(Set<Room> room) {
        this.room = room;
    }
}