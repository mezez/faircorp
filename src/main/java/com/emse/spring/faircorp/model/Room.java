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
    private Set <Window> windows;

    //constructor with non nullable  and default constructor


    public Room() {
    }

    public Room(Integer floor, String name) {
        this.floor = floor;
        this.name = name;
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