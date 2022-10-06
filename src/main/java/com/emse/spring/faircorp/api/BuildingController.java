package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
//@AllArgsConstructor
@NoArgsConstructor
public class BuildingController {

    private BuildingDao buildingDao;

    public BuildingController(BuildingDao buildingDao, WindowDao windowDao, HeaterDao heaterDao) {
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BuildingDto findById(@PathVariable Long id) {
//        return buildingDao.findById(id).map(BuildingDto::new).orElse(new BuildingDto());
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping //create and update
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        //building id should be null
        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getName(), dto.getNumberOfFloors(), dto.getNumberOfRooms()));
//            building = buildingDao.save(new Building(dto.getName(), dto.getFloor(), dto.getCurrentTemperature(), dto.getTargetTemperature()));
        } else {
            building = buildingDao.getReferenceById(dto.getId());

            building.setName(dto.getName());
            if (dto.getNumberOfRooms() != null) {
                building.setNumberOfRooms(dto.getNumberOfRooms());
            }
            if (dto.getNumberOfFloors() != null) {
                building.setNumberOfFloors(dto.getNumberOfFloors());
            }


        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        buildingDao.deleteById(id);
    }

    //TODO MAYBE TURN OFF ALL HEATERS IN THE BUILDING

    //TODO MAYBE OPEN OR CLOSE ALL WINDOWS IN THE BUILDING

}
