package com.hubspot.api.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSession {
  private long duration;
  private List<String> pages;
  private long startTime;
    
}
