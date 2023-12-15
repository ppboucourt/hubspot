package com.hubspot.api.service;

import java.util.List;

import com.hubspot.api.model.Event;

public interface SessionService {
    List<Event> getEvents();
}
