package com.example.adminbot.services;

import com.example.adminbot.models.Session;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<Long, Session> sessions = new ConcurrentHashMap<>();
    public Session getCurrentSession(Long chatId){
        return sessions.get(chatId);
    }
    public boolean hasCurrentSession(Long chatId){
        return sessions.containsKey(chatId);
    }
    public void createNewSession(Long chatId) {
        sessions.put(chatId, new Session());
    }

}
