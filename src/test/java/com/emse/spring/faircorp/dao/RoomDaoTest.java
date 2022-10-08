package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class RoomDaoTest {

    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindARoom(){
        Room room = roomDao.getReferenceById(-10L);
        Assertions.assertThat(room.getName()).isEqualTo("Room1");
        Assertions.assertThat(room.getCurrentTemperature()).isEqualTo(22.3);
    }

    @Test
    public void shouldFindRoomsByName(){
        List<Room> result = roomDao.findRoomsByName("Room1");
        Assertions.assertThat(result).hasSize(1).extracting("id").containsExactly(-10L);
    }

    @Test
    public void  shouldNotFindRoomsByName(){
        List<Room> result = roomDao.findRoomsByName("Ada");
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldFindRoomByBuildingId() {
        List<Room> rooms = roomDao.findByBuildingId(-100L);
        Assertions.assertThat(rooms.isEmpty()).isFalse();

        //based on the initial number of heaters in the db at startup
        Assertions.assertThat(((long) rooms.size())).isEqualTo(1);
    }
}