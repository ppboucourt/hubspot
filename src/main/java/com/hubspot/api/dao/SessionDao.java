package com.hubspot.api.dao;

import java.util.List;

import com.hubspot.api.model.Event;
import com.hubspot.api.model.UserSessions;

public interface SessionDao {
    public List<Event> getEvents();

    public boolean sentUserSessions(UserSessions sessions);
}
