package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class BuildingDaoTest {

    @Autowired
    private BuildingDao buildingDao;

    @Test

    //APPLICATION NEEDS TO BE RUNNING TO HAVE A WORKING DB WITH RECORDS
    //DB RECORDS SHOULD NOT BE TAMPERED WITH TO OBTAIN EXPECTED RESULTS

    public void shouldFindBuilding(){

        Building building = buildingDao.getReferenceById(-100L);
        Assertions.assertThat(building.getName()).isEqualTo("Espace Fauriel");
    }

    @Test
    public void shouldGetAListoFBuildings(){
        List<Building> buildings = buildingDao.findAll();
        Assertions.assertThat(buildings.isEmpty()).isFalse();
    }

    @Test
    public void shouldDeleteABuilding(){
        Building building = buildingDao.getReferenceById(-99L);
        Assertions.assertThat(building.getName()).isEqualTo("La Mer");

        buildingDao.deleteById(-99L);
        Optional<Building> building1 = buildingDao.findById(-99L);
        Assertions.assertThat(building1.isEmpty()).isTrue();
    }

    @Test
    public void shouldFindBuildingsByName(){
        List<Building> buildings = buildingDao.findByName("La Mer");
        Assertions.assertThat(buildings).hasSize(1);
        Assertions.assertThat(buildings.get(0).getId()).isEqualTo(-99L);
    }

}