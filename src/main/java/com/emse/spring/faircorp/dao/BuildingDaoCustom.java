package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface BuildingDaoCustom{
    List<Building> findBuildingsByName(String name);

}