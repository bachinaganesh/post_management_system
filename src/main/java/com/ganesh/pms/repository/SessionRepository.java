package com.ganesh.pms.repository;

import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Session S WHERE S.user.id=:userId")
    void deleteAllSessionsByUser(Long userId);

    @Query("SELECT S FROM Session S WHERE S.user.id=:userId")
    List<Session> findSessionByUserId(Long userId);
}
