package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface WindowDao extends JpaRepository<Window, Long>, WindowDaoCustom {
    @Modifying
    @Query("delete from Window w where w.room.id=:room_id")
    int deleteByRoom(@Param("room_id") Long room_id);
}