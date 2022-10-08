package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.model.Building;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@WebMvcTest(BuildingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BuildingControllerTestOld {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BuildingDao buildingDao;


    @Test
    void shouldLoadAllBuildings() throws Exception {
        given(buildingDao.findAll()).willReturn(List.of(
                createBuilding("Test Building"),
                createBuilding("Test Building Two")
        ));

        mockMvc.perform(get("/api/buildings").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(containsInAnyOrder("Test Building", "Test Building Two")));

    }

//    @Test
//    void findById() {
//    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void switchAllHeatersStatus() {
//    }
//
//    @Test
//    void switchAllWindowsStatus() {
//    }
//
//    @Test
//    void findByName() {
//    }


    private Building createBuilding(String name) {
        return new Building(name, 2, 4);
    }
}