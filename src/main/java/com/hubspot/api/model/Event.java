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
    private long dateVisited;
}
