package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDaoCustomImpl implements RoomDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Room> findRoomsByName(String name) {
        String jpql = "select r from Room r where r.name = :name";
        return entityManager.createQuery(jpql, Room.class)
                .setParameter("name", name)
                .getResultList();
    }

}
