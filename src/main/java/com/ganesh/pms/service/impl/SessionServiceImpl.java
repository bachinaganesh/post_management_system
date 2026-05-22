package com.ganesh.pms.service.impl;

import com.ganesh.pms.models.Session;
import com.ganesh.pms.models.User;
import com.ganesh.pms.repository.SessionRepository;
import com.ganesh.pms.service.ISessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {

    private final SessionRepository sessionRepository;

    @Override
    public List<Session> findSessionsByUser(User user) {
        return sessionRepository.findByUser(user);
    }
}
