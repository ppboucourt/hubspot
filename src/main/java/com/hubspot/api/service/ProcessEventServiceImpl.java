package com.hubspot.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubspot.api.dao.SessionDao;
import com.hubspot.api.model.Event;
import com.hubspot.api.model.UserSession;
import com.hubspot.api.model.UserSessions;

@Service
public class ProcessEventServiceImpl implements ProcessEventService {

  private static int MIN_WINDOW = 600000;
  
  Logger LOG = LoggerFactory.getLogger(ProcessEventServiceImpl.class);

  @Autowired
  SessionDao sessionDao;
    
  @Override
  public void sentEventByUser(List<Event> events) {
    try {
      UserSessions sessions = process(events);
      sessionDao.sentUserSessions(sessions);
    } catch (Exception e) {
      // TODO: handle exception
      LOG.error("Error while processing events", e.getMessage());
    }
  } 

    private UserSessions process(List<Event> events) {
        List<Event> visitorEvents = events;
    
        Map<String, List<Event>> eventsByVisitor = visitorEvents.stream().collect(Collectors.groupingBy(Event::getVisitorId));
    
        Map<String, List<UserSession>> visitorEventMap = new HashMap<>();
        
        eventsByVisitor.forEach((visitorId, eventsL) -> {
            eventsL.sort(
                  (a, b) -> {
                    if (a.getDateVisited() == b.getDateVisited()) return 0;
                    return a.getDateVisited() - b.getDateVisited() > 0 ? 1 : -1;
                  });
    
              visitorEventMap.put(visitorId, new ArrayList<>());
    
              visitorEventsGen(visitorEventMap, visitorId, events);
            });
    
        UserSessions userSessions = new UserSessions();
        userSessions.setSessionsByUser(visitorEventMap);
        return userSessions;
    }

    private void visitorEventsGen(Map<String, List<UserSession>> visitorEventMap, String visitorId, List<Event> events) {
        int i = 0;
        while (i < events.size()) {
          UserSession userSession = new UserSession();
          long startTime = events.get(i).getDateVisited();
          List<String> pages = new ArrayList<>();
    
          userSession.setStartTime(startTime);
          pages.add(events.get(i).getUrl());
          userSession.setPages(pages);
          userSession.setDuration(0);
    
          int j = i + 1;
    
          while (j < events.size() && events.get(j).getDateVisited() - startTime <= MIN_WINDOW) {
            pages.add(events.get(j).getUrl());
            userSession.setDuration(Math.abs(events.get(j).getDateVisited() - startTime));
            j++;
          }
    
          i = j;
          visitorEventMap.get(visitorId).add(userSession);
        }
      }


}
