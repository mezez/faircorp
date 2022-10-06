package com.emse.spring.faircorp.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer floor;

    @NotNull
    private String name;

    private Double currentTemperature;

    private Double targetTemperature;

    //bidirectional list of heaters
    @OneToMany(mappedBy = "room")
    private Set<Heater> heaters;

    //bidirectional list of windows
    @OneToMany(mappedBy = "room")
    private Set<Window> windows;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "building_id")
    private Building building;

    //constructor with non nullable  and default constructor


    public Room() {
    }

    public Room(Integer floor, String name, Building building) {
        this.floor = floor;
        this.name = name;
        this.building = building;
    }

    public Room(Integer floor, String name, Building building, Double currentTemperature, Double targetTemperature) {
        this.floor = floor;
        this.name = name;
        this.building = building;
        this.currentTemperature = currentTemperature != null ? currentTemperature : 0;
        this.targetTemperature = targetTemperature != null ? targetTemperature : 0;
    }

    public Long getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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

    public Set<Heater> getHeaters() {
        return heaters;
    }

    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }

    public Set<Window> getWindows() {
        return windows;
    }

    public void setWindows(Set<Window> windows) {
        this.windows = windows;
    }
}