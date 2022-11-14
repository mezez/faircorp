package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.model.*;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/heaters")
@Transactional
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

    /***
     * Toggle status of a heater
     * :args
     * :id - heater ID
     * ***/
    @PutMapping(path = "/{id}/switch")
    public HeaterDto switchStatus(@PathVariable Long id) {
        Heater heater = heaterDao.findById(id).orElseThrow(IllegalArgumentException::new);
        heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF: HeaterStatus.ON);
        return new HeaterDto(heater);
    }

    @PostMapping //create and update
    public HeaterDto create(@RequestBody HeaterDto dto) {
        Heater heater = null;
        //create new record
        if (dto.getId() == null) {
            Room room = roomDao.getReferenceById(dto.getRoomId());
            if (dto.getPower() != null){
                heater = heaterDao.save(new Heater(dto.getName(), room, dto.getHeaterStatus(), dto.getPower()));
            }else{
                heater = heaterDao.save(new Heater(dto.getName(), room, dto.getHeaterStatus(),null));
            }
        } else {
            //update existing record
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
