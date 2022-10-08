package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
class HeaterDaoTest {

    @Autowired
    private HeaterDao heaterDao;
    @Autowired
    private RoomDao roomDao;

    @Test
    public void shouldFindAHeater(){
        Heater heater = heaterDao.getReferenceById(-9L);
        Assertions.assertThat(heater.getName()).isEqualTo("Heater2");
        Assertions.assertThat(heater.getPower()).isEqualTo(null);
    }

    @Test
    public void shouldGetAListOfHeaters(){
        List<Heater> heaters = heaterDao.findAll();
        Assertions.assertThat(heaters.isEmpty()).isFalse();
    }

    @Test
    public void shouldDeleteRoomHeaters() {
        Room room = roomDao.getReferenceById(-10L);
        List<Long> roomIds = room.getHeaters().stream().map(Heater::getId).collect(Collectors.toList());
        Assertions.assertThat(roomIds.size()).isEqualTo(2);

        heaterDao.deleteByRoom(-10L);
        List<Heater> result = heaterDao.findAllById(roomIds);
        Assertions.assertThat(result).isEmpty();

    }

    @Test
    public void shouldDeleteHeaterByRoom() {
        List<Heater> heaters = heaterDao.findAll();
        Heater firstHeater = heaters.get(0);
        Long firstHeaterId = firstHeater.getId();

        heaterDao.deleteByRoom(firstHeater.getRoom().getId());

        heaters = heaterDao.findAll();
        boolean foundDeletedHeater = false;
        for (Heater heater: heaters) {
            if (heater.getId().equals(firstHeaterId)){
                foundDeletedHeater = true;
                break;
            }
        }
        Assertions.assertThat(foundDeletedHeater).isFalse();
    }

    @Test
    public void shouldFindHeatersByRoomId() {
        List<Heater> heaters = heaterDao.findByRoomId(-10L);
        Assertions.assertThat(heaters.isEmpty()).isFalse();

        //based on the initial number of heaters in the db at startup
        Assertions.assertThat(((long) heaters.size())).isEqualTo(2);
    }
}