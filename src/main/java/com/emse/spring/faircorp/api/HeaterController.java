package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import com.emse.spring.faircorp.security.SpringSecurityConfig;
import lombok.NoArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/heaters")
@Transactional
//@AllArgsConstructor
@NoArgsConstructor
public class HeaterController {

    private HeaterDao heaterDao;
    private RoomDao roomDao;

    public HeaterController(HeaterDao heaterDao, RoomDao roomDao) {
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
    }

    @GetMapping
    public List<HeaterDto> findAll() {
        return heaterDao.findAll().stream().map(HeaterDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HeaterDto findById(@PathVariable Long id) {
        return heaterDao.findById(id).map(HeaterDto::new).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public HeaterDto switchStatus(@PathVariable Long id) {
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON);
        return new HeaterDto(heater);
    }

    @PostMapping //create and update
    public HeaterDto create(@RequestBody HeaterDto dto) {
        Heater heater = null;
        //heater id should be null
        if (dto.getId() == null) {
            Room room = roomDao.getReferenceById(dto.getRoomId());
            heater = heaterDao.save(new Heater(dto.getName(), room, dto.getHeaterStatus()));
//            room = roomDao.save(new Room(dto.getName(), dto.getFloor(), dto.getCurrentTemperature(), dto.getTargetTemperature()));
        } else {
            heater = heaterDao.getReferenceById(dto.getId());

            heater.setName(dto.getName());
            heater.setHeaterStatus(dto.getHeaterStatus());
            if (dto.getPower() != null) {
                heater.setPower(dto.getPower());
            }


        }
        return new HeaterDto(heater);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        heaterDao.deleteById(id);
    }

}
