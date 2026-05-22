package com.ganesh.pms.service;

import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;

import java.util.List;
import java.util.Optional;

public interface ISessionService {

    List<Session> findSessionsByUser(User user);
    void deleteSession(Session session);
    void saveSession(Session session);
    void deleteAllSessions(Long userId);
    List<Session> findSessionByUserId(Long userId);
}
