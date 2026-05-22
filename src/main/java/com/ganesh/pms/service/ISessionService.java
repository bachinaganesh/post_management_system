package com.ganesh.pms.service;

import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;

import java.util.List;

public interface ISessionService {

    List<Session> findSessionsByUser(User user);
}
