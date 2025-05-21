package dev.yunseong.beno2assignment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    private final Long id;
    private final String name;
    private final String email;
}
