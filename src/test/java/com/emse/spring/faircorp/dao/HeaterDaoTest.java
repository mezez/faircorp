package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HeaterDaoTest {

    @Autowired
    private HeaterDao heaterDao;

    @Test
    public void shoudFindAHeater(){
        Heater heater = heaterDao.getReferenceById(-9L);
        Assertions.assertThat(heater.getName()).isEqualTo("Heater2");
        Assertions.assertThat(heater.getPower()).isEqualTo(null);
    }

}