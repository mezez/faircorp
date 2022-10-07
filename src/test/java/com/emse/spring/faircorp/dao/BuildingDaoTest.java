package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BuildingDaoTest {

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindBuilding(){
        //Application should be running to get expected test result
        Building building = buildingDao.getReferenceById(-100L);
        Assertions.assertThat(building.getName()).isEqualTo("Espace Fauriel");
    }
}