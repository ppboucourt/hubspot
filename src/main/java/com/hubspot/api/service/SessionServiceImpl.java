package com.hubspot.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubspot.api.dao.SessionDao;
import com.hubspot.api.model.Event;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionDao sessionDao;

    @Override
    public List<Event> getEvents() {
        List<Event> events = sessionDao.getEvents();
        return events;
    }
    
}
