package dev.yunseong.beno2assignment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Schedule {
    private final Long id;
    private final String title;
    private final String content;
    private final String password;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
