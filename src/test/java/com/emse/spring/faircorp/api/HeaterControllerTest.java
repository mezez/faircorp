package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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

@WebMvcTest(HeaterController.class)
@AutoConfigureMockMvc(addFilters = false)
class HeaterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HeaterDao heaterDao;

    @MockBean
    private RoomDao roomDao;

    @Test
    void shouldLoadHeaters() throws Exception {
        given(heaterDao.findAll()).willReturn(List.of(
                createHeater("heater 1"),
                createHeater("heater 2")
        ));

        mockMvc.perform(get("/api/heaters").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("heater 1", "heater 2")));
    }

    @Test
    void shouldLoadAHeaterAndReturnNullIfNotFound() throws Exception {
        given(heaterDao.findById(999L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/heaters/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(content().string(""));
    }

    @Test
    void shouldLoadAHeater() throws Exception {
        given(heaterDao.findById(999L)).willReturn(Optional.of(createHeater("heater 1")));

        mockMvc.perform(get("/api/heaters/999").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                // the content can be tested with Json path
                .andExpect(jsonPath("$.name").value("heater 1"));
    }

    @Test
    void shouldSwitchHeater() throws Exception {
        Heater expectedHeater = createHeater("heater 1");
        Assertions.assertThat(expectedHeater.getHeaterStatus()).isEqualTo(HeaterStatus.OFF);

        given(heaterDao.findById(999L)).willReturn(Optional.of(expectedHeater));

        mockMvc.perform(put("/api/heaters/999/switch").accept(APPLICATION_JSON))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("heater 1"))
                .andExpect(jsonPath("$.heaterStatus").value("ON"));
    }

    @Test
    void shouldUpdateHeater() throws Exception {
        Heater expectedHeater = createHeater("heater 1");
        expectedHeater.setId(1L);
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.getReferenceById(anyLong())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("heater 1"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void shouldCreateHeater() throws Exception {
        Heater expectedHeater = createHeater("heater 1");
        expectedHeater.setId(null);
        String json = objectMapper.writeValueAsString(new HeaterDto(expectedHeater));

        given(roomDao.getReferenceById(anyLong())).willReturn(expectedHeater.getRoom());
        given(heaterDao.save(any())).willReturn(expectedHeater);

        mockMvc.perform(post("/api/heaters").content(json).contentType(APPLICATION_JSON_VALUE))
                // check the HTTP response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("heater 1"));
    }

    @Test
    void shouldDeleteHeater() throws Exception {
        mockMvc.perform(delete("/api/heaters/999"))
                .andExpect(status().isOk());
    }

    private Heater createHeater(String name) {
        Building building = new Building("Test Building", 2, 4);
        Room room = new Room(1,"S1", building);
        return new Heater(name,room, HeaterStatus.OFF, null);
    }

}