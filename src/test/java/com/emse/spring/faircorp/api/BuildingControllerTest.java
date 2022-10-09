package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.model.*;
import com.emse.spring.faircorp.model.Building;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BuildingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BuildingDao buildingDao;

    @MockBean
    private RoomDao roomDao;

    @MockBean
    private WindowDao windowDao;

    @MockBean
    private HeaterDao heaterDao;

    @Test
    void shouldLoadBuildings() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding("building 1"),
                createBuilding("building 2")
        ));

        mockMvc.perform(get("/api/buildings").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("building 1", "building 2")));
    }

    @Test
    void shouldLoadABuildingAndReturnNullIfNotFound() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    void shouldLoadABuilding() throws Exception {
        given(buildingDao.findById(999L)).willReturn(Optional.of(createBuilding("building 1")));

        mockMvc.perform(get("/api/buildings/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("building 1"));
    }


    @Test
    void shouldUpdateBuilding() throws Exception {
        Building expectedBuilding = createBuilding("building 1");
        expectedBuilding.setId(1L);
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedBuilding));

        given(buildingDao.getReferenceById(anyLong())).willReturn(expectedBuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("building 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }
//
    @Test
    void shouldCreateBuilding() throws Exception {
        Building expectedBuilding = createBuilding("building 1");
        expectedBuilding.setId(null);
        String json = objectMapper.writeValueAsString(new BuildingDto(expectedBuilding));

        given(buildingDao.save(any())).willReturn(expectedBuilding);

        mockMvc.perform(post("/api/buildings").content(json).contentType(APPLICATION_JSON_VALUE))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("building 1"));
    }

    @Test
    void shouldDeleteBuilding() throws Exception {
        mockMvc.perform(delete("/api/buildings/999"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSwitchStatusOfAllBuildingHeaters() throws Exception {
        Building expectedBuilding = createBuilding("building 1");
//        expectedBuilding.setId(1L);
        Heater expectedHeater = createHeater("H1", expectedBuilding);
        expectedHeater.setHeaterStatus(HeaterStatus.OFF);

        given(buildingDao.save(any())).willReturn(expectedBuilding);
        given(heaterDao.save(any())).willReturn(expectedHeater);

        mockMvc.perform(put("/api/buildings/{0}/switchAllHeaters/{1}",1,"off").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string("Heaters have been turned off"));
    }

    @Test
    void shouldSwitchAllWindowsStatus() throws Exception {
        Building expectedBuilding = createBuilding("building 1");
//        expectedBuilding.setId(1L);
        Window expectedWindow = createWindow("H1", expectedBuilding);
        expectedWindow.setWindowStatus(WindowStatus.CLOSED);

        given(buildingDao.save(any())).willReturn(expectedBuilding);
        given(windowDao.save(any())).willReturn(expectedWindow);

        mockMvc.perform(put("/api/buildings/{0}/switchAllWindows/{1}",1,"closed").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string("CLOSED action completed for all building windows"));
    }

    private Building createBuilding(String name) {
        return new Building(name, 2, 4);
    }

    private Window createWindow(String name, Building building) {
//            Building building = new Building("Test Building", 2, 4);
            Room room = new Room(1,"S1", building);
            return new Window(name, WindowStatus.OPEN, room);
    }

    private Heater createHeater(String name, Building building) {
//            Building building = new Building("Test Building", 2, 4);
            Room room = new Room(1,"S1", building);
            return new Heater( name ,room, HeaterStatus.ON);
    }

}