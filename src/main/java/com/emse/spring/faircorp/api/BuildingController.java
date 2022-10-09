package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.*;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
//@AllArgsConstructor
@NoArgsConstructor
public class BuildingController {

    private BuildingDao buildingDao;
    private HeaterDao heaterDao;
    private RoomDao roomDao;
    private WindowDao windowDao;

    public BuildingController(BuildingDao buildingDao, RoomDao roomDao, WindowDao windowDao, HeaterDao heaterDao) {
        this.buildingDao = buildingDao;
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
        this.windowDao = windowDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @PostMapping //create and update
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        //create new record
        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getName(), dto.getNumberOfFloors(), dto.getNumberOfRooms()));
        } else {
            //update existing record
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

    /***
     * Turn all heaters in a building on or off
     * :args
     * :id - building ID
     * :newStatus - intended status of all heaters, either 'ON' or 'OFF'
     * ***/
    @PutMapping(path = "/{id}/switchAllHeaters/{newStatus}")
    public String switchAllHeatersStatus(@PathVariable Long id,@PathVariable String newStatus){
        try{
            //get rooms by building id
            List<Room> rooms = roomDao.findByBuildingId(id);

            for (Room room: rooms ) {
                //get heaters
                List<Heater> heaters = heaterDao.findByRoomId(room.getId());
                for (Heater heater: heaters) {
                    heater.setHeaterStatus(newStatus.toLowerCase().equals("on") ? HeaterStatus.ON: HeaterStatus.OFF);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "Heaters have been turned "+newStatus;

    }


    /***
     * Open or close all windows in a building
     * :args
     * :id - building ID
     * :newStatus - intended status of all heaters, either 'OPEN' or 'CLOSE'
     * ***/
    @PutMapping(path = "/{id}/switchAllWindows/{newStatus}")
    public String switchAllWindowsStatus(@PathVariable Long id,@PathVariable String newStatus){
        try{
            //get rooms by building id
            List<Room> rooms = roomDao.findByBuildingId(id);

            for (Room room: rooms ) {
                //get heaters
                List<Window> windows = windowDao.findByRoomId(room.getId());
                for (Window window: windows) {
                    window.setWindowStatus(newStatus.toLowerCase().equals("open") ? WindowStatus.OPEN: WindowStatus.CLOSED);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return newStatus.toUpperCase() + " action completed for all building windows";

    }

    /***
     * Find buildings by name
     * :query param
     * :name - building name
     * ***/
    @GetMapping(path = "/findByName")
    public List<BuildingDto> findByName(@RequestParam String name){

        List<BuildingDto> buildings =  buildingDao.findByName(name).stream().map(BuildingDto::new).collect(Collectors.toList());;
        return  buildings;

    }

}
