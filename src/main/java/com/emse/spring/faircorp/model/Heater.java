package com.emse.spring.faircorp.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "heater")
public class Heater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private Long power;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "heaterStatus",nullable = false)
    @Enumerated(EnumType.STRING)
    HeaterStatus heaterStatus;

    public Heater(String name, Room room, HeaterStatus heaterStatus) {
        this.name = name;
        this.room = room;
        this.heaterStatus = heaterStatus;
    }

    public Heater (){}

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

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public HeaterStatus getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(HeaterStatus heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
}