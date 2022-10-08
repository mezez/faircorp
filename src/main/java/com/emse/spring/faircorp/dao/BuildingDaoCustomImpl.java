//package com.emse.spring.faircorp.dao;
//
//import com.emse.spring.faircorp.model.Building;
//import com.emse.spring.faircorp.model.Window;
//import com.emse.spring.faircorp.model.WindowStatus;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//public class BuildingDaoCustomImpl implements BuildingDaoCustom{
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<Building> findBuildingsByName(String name) {
//        String jpql = "select b from Building w where b.name = :name";
//        return entityManager.createQuery(jpql, Building.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//
//}
