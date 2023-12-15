package com.hubspot.api.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private String url;
    private String visitorId;
    // timestamps should be mapped as long to be transformed later 
    //into Date or other date/time class
    private long dateVisited;
}
