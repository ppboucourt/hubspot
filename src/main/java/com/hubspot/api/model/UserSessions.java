package com.hubspot.api.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSessions {
    Map<String, List<UserSession>> sessionsByUser;
}
