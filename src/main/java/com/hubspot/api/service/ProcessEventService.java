package com.hubspot.api.service;

import java.util.List;

import com.hubspot.api.model.Event;

public interface ProcessEventService {
    public void sentEventByUser(List<Event> events);
}
