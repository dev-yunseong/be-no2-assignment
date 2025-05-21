package dev.yunseong.beno2assignment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequestDto {
    private final String title;
    private final String content;
    private final String password;
    private final Long userId;
}

