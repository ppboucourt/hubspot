package com.hubspot.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hubspot.api.model.Event;
import com.hubspot.api.service.ProcessEventService;
import com.hubspot.api.service.SessionService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.*;

@RestController
@RequestMapping(value = "/hubspot/")
public class SessionApiController {

    Logger LOG = LoggerFactory.getLogger(SessionApiController.class);

    private List<Event> events;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ProcessEventService processEventService;
    
    /**
     * This GET api it's the entrypoint to trigger our session generator.
     * It access the dataset of events, trigger the logic to
     * generate sessions and then publish it via post to 
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<String> generateSession() {
        events = Collections.emptyList();
        try {
            
            // Get Events
            events = sessionService.getEvents();
            
            if(events.isEmpty()) {
                LOG.info("Unable to get events dataset from Api");
                return ResponseEntity.
                status(HttpStatus.BAD_REQUEST).
                body("Unable to get events dataset from Api\n");
            }

            //Process events
            processEventService.sentEventByUser(events);

        } catch (Exception e) {
            LOG.error("Error while processing events", e.getMessage());
            return ResponseEntity.
            status(HttpStatus.BAD_REQUEST).
            body(e.getMessage());
        }
        return ResponseEntity.
        status(HttpStatus.OK).
        body("Events processed successfully\n");;
    }
}
