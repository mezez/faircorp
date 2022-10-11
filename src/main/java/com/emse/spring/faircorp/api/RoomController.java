package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
@NoArgsConstructor
public class RoomController {

    private RoomDao roomDao;
    private BuildingDao buildingDao;
    private WindowDao windowDao;
    private HeaterDao heaterDao;

    public RoomController(RoomDao roomDao, BuildingDao buildingDao, WindowDao windowDao, HeaterDao heaterDao) {
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
        this.windowDao = windowDao;
        this.heaterDao = heaterDao;
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @PostMapping //create and update
    public RoomDto create(@RequestBody RoomDto dto) {
        Room room = null;

        Building building = buildingDao.getReferenceById(dto.getBuildingId());
        Window window = null;

        //validate room floor
        if (building.getNumberOfFloors() < dto.getFloor())
            throw new IllegalStateException("Maximum number of floors for the " + building.getName() + " building is " + building.getNumberOfFloors());

        //create new room
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), building, dto.getCurrentTemperature(), dto.getTargetTemperature()));
        } else {
            //update existing room
            room = roomDao.getReferenceById(dto.getId());

            room.setFloor(dto.getFloor());
            room.setName(dto.getName());
            if (dto.getTargetTemperature() != null) {
                room.setTargetTemperature(dto.getTargetTemperature());
            }
            if (dto.getCurrentTemperature() != null) {
                room.setCurrentTemperature(dto.getCurrentTemperature());
            }


        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        roomDao.deleteById(id);
    }

    /***
     * Toggle status of all windows in a room
     * :args
     * :id - room ID
     * ***/
    @PutMapping(path = "/{id}/switchWindows")
    public List<WindowDto> switchWindowsStatus(@PathVariable Long id) {
        List<WindowDto> windowResponse = new ArrayList<>();
        try {
            List<Window> windows = windowDao.findByRoomId(id);

            for (Window window : windows) {
                window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED : WindowStatus.OPEN);
                windowResponse.add(new WindowDto(window));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return windowResponse;
    }

    /***
     * toggle status of all heaters in a room
     * :args
     * :id - room ID
     * ***/
    @PutMapping(path = "/{id}/switchHeaters")
    public List<HeaterDto> switchHeatersStatus(@PathVariable Long id) {
        List<HeaterDto> heaterResponse = new ArrayList<>();
        try {
            List<Heater> heaters = heaterDao.findByRoomId(id);


            for (Heater heater : heaters) {
                heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF : HeaterStatus.ON);
                heaterResponse.add(new HeaterDto(heater));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return heaterResponse;
    }

}
