package com.hubspot.api.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hubspot.api.model.Event;
import com.hubspot.api.model.Events;
import com.hubspot.api.model.UserSession;
import com.hubspot.api.model.UserSessions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class SessionDaoImpl implements SessionDao {

    

    @Value("${hubspot.api.get.events}")
    private String apiGetEvents;


    @Value("${hubspot.api.post.sessions}")
    private String apiPostSessions;

    @Override
    public List<Event> getEvents() {
        Events events = new Events();
        events.setEvents(new ArrayList<>());
        try {
            RestTemplate restTemplate = new RestTemplate();
            events = restTemplate.getForObject(apiGetEvents, Events.class);
            return events.getEvents();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return events.getEvents();
    }

    @Override
    public boolean sentUserSessions(UserSessions sessions) {
        String response;
        try {
            Map<String, List<UserSession>> map = new HashMap<>();
            map = sessions.getSessionsByUser();

            HttpEntity<Map<String, List<UserSession>>> request = new HttpEntity<>(map);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserSession> result = restTemplate.postForEntity(apiPostSessions, request, UserSession.class);
            response = result.getStatusCode().toString();
            
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        if(response.isEmpty())
            return false;
        return true;
    }


    
}
